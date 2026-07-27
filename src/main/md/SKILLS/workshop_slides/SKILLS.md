---
name: workshop_slides
description: Create or revise interactive workshop slide decks as self-contained HTML presentations with module-based navigation and companion quiz JSON. Use when the user wants workshop slides, training decks, architecture deep-dive slides, or slide content generated from source material.
---

# Workshop Slides

Use this skill when the user wants a workshop turned into an interactive slide deck instead of a plain article.

## Default Behavior

- Build a self-contained HTML presentation unless the user asks for another format.
- Pair the presentation with a `{topic}_quiz.json` file when quizzes are requested.
- Keep output paths relative to the current task unless the user explicitly gives a destination.
- Reuse existing local workshop examples if they exist; otherwise follow the structure below.

## Workflow

1. Read the source material first.
   Source material can include notes, markdown, code paths, PRs, docs, architecture diagrams, or user bullets.
2. Produce a module plan before drafting the full deck.
   The plan should name each module, explain its scope, and note the expected quiz coverage.
3. After approval, generate the slide deck and quiz artifacts.
4. Verify navigation, quiz flow, and JSON structure before handing off.

## Deck Structure

Use a single HTML file with inline CSS and JavaScript.

- Include a progress bar, header, slide container, table of contents, and previous/next navigation.
- Support keyboard navigation: next, previous, first slide, last slide, TOC toggle, and retry failed quiz questions.
- Persist progress with a unique `STORE_KEY` derived from the topic.
- Organize deck content as modules. Each module should usually contain:
  - an intro or objective slide
  - several content slides
  - a short recap slide
  - a quiz slide at the end of the module

Prefer a JavaScript structure similar to:

- `QUIZ_DATA` for quiz content
- `MODULE_NAMES` for module labels
- `SLIDES` for ordered slide objects
- rendering helpers such as `render`, `renderQuiz`, `go`, `toggleTOC`, `save`, and `load`

Do not hard-code implementation details from a prior workshop if they do not fit the current topic.

## Content Guidance

- Keep slides dense enough to teach something real, but do not pad them to hit arbitrary word counts.
- Use tables, code blocks, callouts, and ASCII diagrams where they materially improve comprehension.
- Favor concrete names from the source material: services, configs, classes, tables, commands, files, and failure modes.
- Merge weak slides instead of creating filler.
- End each module with a recap that sets up the quiz.

## Quiz Rules

- Use 4 options per question unless the user asks for another format.
- In JSON, keep the correct answer at index `0`.
- Shuffle options at render time, not in the stored JSON.
- Make the shuffle deterministic from a stable seed such as the question ID.
- Include explanations grounded in the workshop content.
- Track failed questions so the learner can retry them.

## ADQ Mode

If the user asks for ADQ or an architecture deep-dive style workshop:

- Focus on systems, boundaries, data flow, deployment shape, and external dependencies.
- Mention important traits, classes, topics, queues, tables, and config files.
- Prefer architecture and operational reasoning over method-by-method walkthroughs.
- Use diagrams and production-flavored examples wherever the source material supports them.

## Review Checklist

Before finishing:

- Confirm the HTML is self-contained.
- Confirm the `STORE_KEY` is unique for that workshop.
- Confirm every quiz question has 4 options, `answer: 0`, and an explanation.
- Confirm the slide order, TOC, and keyboard navigation are consistent.
- Confirm claims in the slides are supported by the provided source material.

To generate a similar interactive, step-by-step tutorial for any documentation, book, or technical material, you can use the **Master Prompt Template** below or create a custom **Agent Skill**.

---

### 1. 📋 Reusable Master Prompt Template

Copy and paste this template, replacing the `[URL or Topic Name]` and specific requirements:

```markdown
Generate a comprehensive, downloadable step-by-step interactive HTML tutorial based on the content at:
URL: [Insert Documentation URL or Topic Name]

Requirements:
1. UI Theme: [Light Theme / Dark Theme] with clean modern typography (Inter font, sleek cards, responsive layout).
2. Curriculum Structure:
   - Divide the material into 12 to 20 progressive, structured learning steps across core modules.
   - Include operational knowledge, architecture, edge cases, and configuration tuning.
3. Keyword Retention System:
   - Every slide MUST highlight at least 2 to 3 key terms/concepts (with concise definitions).
   - Every slide MUST feature an interactive "Keyword Retention Check" quiz widget to test understanding before marking the step as mastered.
4. Track Trace Progress Tracker:
   - Display a top visual progress bar (0% - 100%) showing current step and overall % mastered.
   - Include an interactive sidebar step timeline showing step nodes (Active, Mastered, Unseen).
5. Visual Process Diagrams:
   - Embed inline SVG sequence/architecture diagrams for key workflows and concepts.
6. Parameter Tuning & Impact Analysis (for technical docs):
   - Include dedicated slides detailing key configuration parameters, default values, and their direct operational impact (throughput, latency, memory, blocking/backpressure).
7. Downloadable File:
   - Embed a 1-click "📥 Download Tutorial File" button that downloads the entire standalone HTML file with zero external JS dependencies so it works 100% offline.
   - Include a "📖 Master Glossary" modal listing all key terms.
```

---

### 2. ⚡ Key Directives to Include in Your Prompt

When requesting a tutorial for a new topic, make sure to specify:

| Requirement Area | What to Ask For |
|---|---|
| **Theme & Style** | `Light Theme` or `Dark Theme`, specifying modern CSS variables, glassmorphism, or clean slate card layouts. |
| **Retention Mechanism** | Ask for `2-3 keywords per slide` + `interactive quiz widget per slide` + `mastery counter`. |
| **Progress Tracking** | Ask for a `track trace progress bar` showing live completion % and an interactive sidebar timeline. |
| **Practical / Impact Focus** | Ask for `Parameter Tuning & Impact Matrix` showing how configuration choices affect performance (e.g. latency, throughput, blocking). |
| **Portability** | Explicitly request a `self-contained standalone single-file HTML app` with an embedded download button. |

---

### 3. 🛠️ Custom Antigravity Skill Definition (`SKILL.md`)

If you want the agent to automatically apply this pattern whenever you ask for a **"tutorial"** or **"interactive guide"**, you can save the following instructions into your `.gemini/antigravity/skills/interactive-tutorial-generator/SKILL.md`:

```yaml
---
name: interactive-tutorial-generator
description: Generates step-by-step interactive HTML tutorials with track-trace progress bars, keyword retention checks, visual SVG diagrams, parameter impact matrices, and 1-click offline HTML export from any documentation or URL.
---

# Interactive Tutorial Generator Skill

When the user asks to generate a tutorial or interactive guide from documentation/URLs:

1. **Extract Core Material**:
   - Read the target documentation/URL thoroughly.
   - Group topics into 12-20 progressive steps across 6-8 modules.

2. **Keyword Retention System**:
   - Ensure EACH slide has 2-3 highlighted keywords with clear definitions.
   - Add a multiple-choice retention check quiz to test keyword memory per slide.

3. **Track Trace Progress Bar**:
   - Add a header progress bar (0-100%) calculating completed steps and mastered keywords.
   - Add a sidebar step track listing module steps with status badges (✓ Mastered, Active).

4. **Visual Diagrams & Parameter Impact**:
   - Embed clean SVG diagrams for sequence flows and component architecture.
   - Create parameter impact tables showing option names, defaults, and operational impact.

5. **Standalone HTML Deliverable**:
   - Write a self-contained `.html` file with embedded CSS, JS, and SVGs.
   - Include a 1-click `📥 Download Tutorial File` button and a `📖 Master Glossary` modal.
```

## Output Naming

Use topic-based names unless the user specifies otherwise.

- HTML: `{topic_name}_workshop.html` or `{topic_name}.html`
- Quiz JSON: `{topic_name}_quiz.json`

If the user requests revisions to an existing workshop, preserve the current naming and update only what is necessary.
