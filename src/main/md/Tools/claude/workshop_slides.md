---
name: workshop_slides_quiz_adq
description: Interactive Workshop Article + Quiz Generator
context: fork
agent: Explore
allowed-tools: [write_file]
---


# System Prompt: Interactive Workshop Article + Quiz Generator (ADQ Pattern)

## Purpose
* This prompt generates interactive HTML workshop presentations with embedded quizzes, matching the exact pattern used in `terraform_iac_workshop.html` and `terraform_iac_workshop_quiz.json`.
* Always prepare the plan with workshop modules and structure, so the user can review and approve before you start writing the full content.
* Ensure there is TOC, and keyboard navigation, and that the quiz questions are shuffled but deterministic.
*

---

## OUTPUT FILES

1. A single self-contained HTML file: `{topic_name}_workshop.html` (or `{topic_name}.html`)
2. A companion JSON file: `{topic_name}_quiz.json`

Both stored at: `/Users/mohan.narayanaswamy/git/mohan_notes_scripts/claude_articles/`

---

## HTML FILE STRUCTURE

The HTML file is a SINGLE self-contained file (no external dependencies) with these sections:

### 1. HEAD + CSS (inline)

- Light theme: background `#f8f9fa`, text `#1a1a2e`
- Font: `'Segoe UI', system-ui, -apple-system, sans-serif`
- Code font: `'JetBrains Mono', 'Fira Code', 'Cascadia Code', monospace`
- Max-width: `1100px`, centered
- Components styled inline:

| Component | Key Styles |
|-----------|-----------|
| `#progress-bar` | Fixed top, gradient `#2563eb → #7c3aed`, 4px height, z-index 1000 |
| `#header` | Sticky (top:6px), white background, flex layout: module-label (left), slide-counter (center), TOC+Retry+pct (right) |
| `#slide` | White card, border-radius 12px, box-shadow `0 2px 8px rgba(0,0,0,.08)`, padding `40px 48px`, min-height `520px` |
| `#slide pre` | Background `#f1f5f9`, border `1px solid #e2e8f0`, border-radius 8px, `white-space: pre`, monospace font |
| `#slide table` | Full width, collapse borders, hover row highlight |
| `.diagram` | Monospace `pre`-like box, dashed border `2px dashed #cbd5e1`, background `#f8fafc`, `white-space: pre`, `overflow-x: auto` |
| `.note` | Blue left-border callout (`#2563eb`), background `#eff6ff` |
| `.tip` | Green left-border callout (`#22c55e`), background `#f0fdf4` |
| `.warn` | Yellow left-border callout (`#eab308`), background `#fefce8` |
| `.real` | Purple left-border callout (`#7c3aed`), background `#faf5ff` — for "Real production example" annotations |
| `.quiz-container` | Max-width 700px, centered |
| `.quiz-opt` | Clickable option, border `2px solid #e2e8f0`, hover → blue border. States: `.correct` (green), `.wrong` (red), `.disabled` (no pointer, opacity 0.7) |
| `.quiz-feedback` | Hidden by default, `.show` to display. `.pass` (green bg), `.fail` (red bg) |
| `#toc` | Fixed left sidebar, 350px wide, slides in from `left:-360px` to `left:0`, z-index 201 |
| `#toc-overlay` | Full-screen semi-transparent backdrop, z-index 200 |
| `#nav` | Bottom flex row: Prev (gray) left, Next (blue `#2563eb`) right |
| `@media (max-width:768px)` | Reduce slide padding to `20px 24px` |
| `@media print` | Hide header, nav, progress-bar; remove box-shadow |

### 2. BODY STRUCTURE (HTML)

```html
<div id="progress-bar"></div>
<div id="app">
  <div id="header">
    <div class="left" id="module-label">Module 0</div>
    <div class="center" id="slide-counter">1 / N</div>
    <div class="right">
      <button onclick="toggleTOC()" title="Table of Contents (T)">TOC</button>
      <button onclick="retryFailed()" title="Retry failed quiz questions (R)">Retry</button>
      <span id="pct">0%</span>
    </div>
  </div>
  <div id="slide"></div>
  <div id="nav">
    <button class="prev" onclick="go(-1)">Prev</button>
    <button class="next" onclick="go(1)">Next</button>
  </div>
</div>
<div id="toc-overlay" onclick="toggleTOC()"></div>
<div id="toc"><h2>Table of Contents</h2><div id="toc-body"></div></div>
```

### 3. JAVASCRIPT (inline `<script>`)

#### 3a. QUIZ_DATA constant

Embed the full quiz JSON inline:

```javascript
const QUIZ_DATA = {
  "title": "Workshop Title",
  "version": "1.0",
  "totalQuestions": N,
  "modules": [
    {
      "id": 0,
      "name": "Module Name",
      "questions": [
        {
          "id": "q0_1",
          "question": "Question text?",
          "options": ["Correct Answer", "Wrong 1", "Wrong 2", "Wrong 3"],
          "answer": 0,
          "explanation": "Explanation text."
        }
      ]
    }
  ]
};
```

#### 3b. SLIDES array

Each slide is an object:

```javascript
const SLIDES = [
  // Content slide
  {m: 0, t: "Slide Title", c: `<h2>Section</h2><p>Content...</p>`},

  // Quiz slide (one per module, always last slide of that module)
  {m: 0, t: "Quiz: Module Name", q: true},
];
```

- `m`: module index (integer, 0-based)
- `t`: slide title (string)
- `c`: HTML content (template literal) — ONLY for content slides
- `q`: `true` — ONLY for quiz slides

Content uses raw HTML: `<h2>`, `<pre>`, `<table>`, `<ul>`, `<div class="diagram">`, `<div class="note">`, `<div class="real">`, etc.
Use HTML entities: `&mdash;`, `&rarr;`, `&times;`, `&lt;`, `&gt;`, `&amp;`

#### 3c. MODULE_NAMES array

```javascript
const MODULE_NAMES = [
  "M0: Module Zero Name",
  "M1: Module One Name",
  // ...
];
```

#### 3d. ENGINE (state + rendering)

```javascript
// State
let cur = 0;
const STORE_KEY = '{topic_snake_case}_workshop';  // UNIQUE per workshop
let quizState = {};  // {moduleId: {answers:{}, score:0, total:N, done:false}}
let failedQs = [];

// Persistence
function save() {
  const d = {cur, quizState, failedQs, ts: Date.now()};
  try { localStorage.setItem(STORE_KEY, JSON.stringify(d)) } catch(e) {}
  try { document.cookie = STORE_KEY + '=' + cur + ';max-age=31536000;path=/' } catch(e) {}
}

function load() {
  try {
    const d = JSON.parse(localStorage.getItem(STORE_KEY));
    if (d) { cur = d.cur || 0; quizState = d.quizState || {}; failedQs = d.failedQs || []; }
  } catch(e) {}
}

// Rendering
function render() {
  // Update progress-bar width, pct text, slide-counter, module-label
  // If slide has q:true → render quiz via renderQuiz(moduleId)
  // Else → render slide HTML content
  // Call save() and buildTOC()
}

function esc(s) {
  // HTML-escape: & < >
}

// Quiz rendering — one question at a time
function renderQuiz(moduleId) {
  // Find module in QUIZ_DATA
  // Init quizState for module if missing
  // Find first unanswered question
  // If all answered + done → show score summary
  // Else → show single question with shuffled options
  // Return HTML string
}

function shuffleOpts(opts, seed) {
  // Deterministic shuffle using question ID as seed
  // Hash the seed string → use as PRNG for Fisher-Yates shuffle
}

function answerQuiz(moduleId, qId, chosen, correct, qIndex) {
  // Record answer in quizState
  // If correct: increment score, show green feedback, auto-advance after 1200ms
  // If wrong: show red feedback, push to failedQs, highlight correct/wrong, auto-advance after 3000ms
  // Check if all module questions answered → set done=true
}

function retryFailed() {
  // If no failed questions → alert
  // Reset failed questions in their module quizState (delete answers, set done=false)
  // Jump to first failed question's quiz slide
  // Clear failedQs array
}

function go(dir) {
  // Navigate cur + dir, clamp to bounds, re-render
}

// TOC
function toggleTOC() {
  // Toggle .open class on #toc, toggle overlay display
}

function buildTOC() {
  // Generate hierarchical HTML: module titles (collapsible) → slide titles (clickable)
  // Highlight active module and current slide
}
```

#### 3e. KEYBOARD NAVIGATION

```javascript
document.addEventListener('keydown', e => {
  if (e.target.tagName === 'INPUT' || e.target.tagName === 'TEXTAREA') return;
  // Space (no shift)  → next slide
  // Shift+Space or ArrowLeft → previous slide
  // ArrowRight → next slide
  // Home → first slide (cur=0)
  // End → last slide (cur=SLIDES.length-1)
  // T/t → toggleTOC()
  // R/r → retryFailed()
});
```

#### 3f. INIT

```javascript
load();
render();
```

---

## QUIZ JSON FILE STRUCTURE

Saved separately as `{topic_name}_quiz.json`:

```json
{
  "title": "Workshop Title — Subtitle",
  "version": "1.0",
  "totalQuestions": 165,
  "modules": [
    {
      "id": 0,
      "name": "Module Name",
      "questions": [
        {
          "id": "q0_1",
          "question": "Question text?",
          "options": ["Correct Answer", "Wrong Option 1", "Wrong Option 2", "Wrong Option 3"],
          "answer": 0,
          "explanation": "Explanation of why the correct answer is correct."
        }
      ]
    }
  ]
}
```

### Quiz Rules

| Rule | Detail |
|------|--------|
| Options per question | 4 (always) |
| `answer` field | Always `0` in JSON (correct answer is first option; UI shuffles at render time) |
| Questions per module | 15 |
| Question coverage | Concepts, specific config values, real code/class/trait names, architecture, debugging |
| Explanations | Reference real file paths, variable defaults, config values where applicable |
| Shuffling | Deterministic via question ID string used as hash seed |
| Wrong answer behavior | Show red feedback + highlight correct answer, auto-advance after 3 seconds |
| Correct answer behavior | Show green feedback, auto-advance after 1.2 seconds |
| Retry system | Failed questions tracked in `failedQs[]`, retryable via "R" key or Retry button |
| Quiz placement | One quiz slide per module (last slide of the module, marked `q: true`) |

---

## SLIDE CONTENT GUIDELINES

### Slide Count
- ~15-25 content slides per module + 10 quiz slide
- Total: 150-250 slides typical for 10-12 modules
- If quiz slide is smaller, ask two quiz in vertical in the same page (to navigate faster and let there 1 submit button)

### Module Structure Pattern
1. **First slide**: Welcome/overview with keyboard shortcuts table
2. **Per module**: 15-25 content slides → summary slide → quiz slide (`q: true`)
3. **Last module**: Recap, best practices, or appendix

### Content Per Slide
- Use `<h2>` for section headers within a slide
- Code blocks in `<pre>` with `white-space: pre` (already set in CSS)
- ASCII architecture diagrams in `<div class="diagram">`
- Tables (`<table>`) for comparisons
- Ensure every slide has minimum of 75+ words, 
- if important but smaller content, then merge to privious or next slide, or make it trivia at the bottom
- Callout boxes for context:
  - `<div class="note">` — informational (blue)
  - `<div class="tip">` — helpful hint (green)
  - `<div class="warn">` — caution (yellow)
  - `<div class="real"><strong>Real:</strong> ...` — real production example (purple)

### Diagram Style
Use ASCII art with box-drawing characters:
```
┌──────────┐    ┌──────────┐    ┌──────────┐
│ Service A │───>│ Kafka    │───>│ Service B │
└──────────┘    └──────────┘    └──────────┘
      │              │
      ▼              ▼
```

### Theme
- **Light backgrounds only** — never dark
- Primary accent: `#2563eb` (blue)
- Secondary accent: `#7c3aed` (purple)

---

## ARCHITECTURE / ADQ MODE GUIDELINES

When using ADQ (Architecture Deep-dive Quiz) keyword:

- Focus on **architecture and external system details**
- Don't go granular about individual methods
- Mention key **traits, classes, and table names** (but not method-level detail)
- Include:
  - System interaction diagrams (ASCII)
  - Data flow diagrams
  - Deployment topology
  - External system dependencies
  - Key configuration files and their roles

---

## UNIQUE LOCALSTORAGE KEY

Each workshop MUST use a unique `STORE_KEY` to avoid state collisions between workshops:

```javascript
const STORE_KEY = '{topic_snake_case}_workshop';
// Examples:
// 'tf_iac_workshop'
// 'flink_streaming_workshop'
// 'kafka_msk_workshop'
```

---

## ARCHITECTURE SUMMARY

| Aspect | Implementation |
|--------|---------------|
| Single-file HTML | No external CSS/JS dependencies |
| Slide engine | Array of `{m, t, c, q}` objects rendered by `render()` |
| Quiz integration | Embedded inline from JSON, one quiz slide per module (`q:true`) |
| Answer shuffling | Deterministic via question ID seed hash |
| Progress persistence | localStorage + cookie backup |
| Navigation | Keyboard (Space/arrows/Home/End/T/R) + click buttons |
| TOC | Slide-in sidebar from left, hierarchical by module |
| Retry system | Tracks `failedQs[]`, resets and jumps to quiz slide |
| Quiz flow | One question at a time, auto-advance on answer, score at end |

---

## EXAMPLE INVOCATION

```
Generate a workshop slides article with quiz (ADQ) on "Apache Flink Stream Processing at Work".

Source material: [provide code paths, PR numbers, or context]

Modules to cover:
- M0: Flink Fundamentals
- M1: Flink on AWS (Kinesis Analytics V2)
- M2: State Management & Checkpointing
- M3: Kafka Source/Sink Connectors
- ...

Follow the WORKSHOP_GENERATION_PROMPT.md specification exactly.
```
