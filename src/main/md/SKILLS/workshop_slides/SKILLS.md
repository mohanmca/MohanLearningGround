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
- Use topic-based output names:
  - HTML: `{topic_name}_workshop.html` or `{topic_name}.html`
  - Quiz: `{topic_name}_quiz.json`
- When revising an existing workshop, preserve its filenames and change only what the request requires.

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
- Keep CSS, JavaScript, diagrams, fonts, and other runtime assets inline or use system fonts so the deck works fully offline without network dependencies.
- Organize deck content as modules. Each module should usually contain:
  - an intro or objective slide
  - several content slides
  - a short recap slide
  - a quiz slide at the end of the module

Prefer a JavaScript structure similar to:

- `QUIZ_DATA` for quiz content
- `CONCEPTS` for concept definitions, dependencies, and mastery state
- `MODULE_NAMES` for module labels
- `SLIDES` for ordered slide objects
- rendering helpers such as `render`, `renderQuiz`, `go`, `toggleTOC`, `save`, and `load`

Do not hard-code implementation details from a prior workshop if they do not fit the current topic.

## Learning Interface

Keep learning status visible without obscuring the content:

- Use a persistent sidebar timeline with step number, module, title, concept count, current state, and completion state.
- Show the current module as a compact badge above the slide title.
- Present 2–3 essential terms near the start of each step in a consistent memory box with short definitions.
- Place the retrieval check inside the step, after the explanation and practice, so the learner receives feedback in context.
- Keep previous/next controls and a concise learning summary visible at the bottom.
- Provide a searchable, deduplicated glossary grouped by concept; link each term back to its source slides.
- Show navigation position, activity completion, and concept mastery as separate measures. Never infer keyword or concept mastery by multiplying completed steps.
- Use a responsive layout: collapse the sidebar on narrow screens, allow tables and code to scroll, and keep controls keyboard and touch accessible.
- Respect accessibility basics: semantic controls, visible focus, sufficient contrast, non-color status cues, reduced-motion preferences, and text alternatives for diagrams.

## Content Guidance

- Keep slides dense enough to teach something real, but do not pad them to hit arbitrary word counts.
- Use tables, code blocks, callouts, and inline SVG or ASCII diagrams where they materially improve comprehension.
- Favor concrete names from the source material: services, configs, classes, tables, commands, files, and failure modes.
- Merge weak slides instead of creating filler.
- End each module with a recap that sets up the quiz.
- For technical topics, cover relevant configuration defaults and their effects on throughput, latency, memory, blocking, or backpressure.
- Use a parameter-impact matrix when configuration matters. Include the exact option, scope or location, default when supported by the source, operational effect, risk, observable symptom, and safe validation approach.

## SVG and Visual Explanation

Use diagrams to express relationships that prose cannot show as quickly:

- Prefer inline, responsive SVG with a `viewBox`; avoid fixed dimensions that overflow smaller screens.
- Use labeled nodes and directional connectors for architecture, sequence, recovery, backpressure, and data-flow diagrams.
- Visually distinguish normal paths, persistence or recovery paths, blocked paths, and optional paths with both labels and line styles, not color alone.
- Add arrowheads, short connector labels, and a clear diagram title or question.
- Use comparison diagrams for healthy versus misconfigured states and connect them to observable operational consequences.
- Keep text legible at presentation scale and avoid decorative complexity.
- Add an accessible text summary or equivalent structured explanation for every substantive diagram.
- Where useful, turn a diagram into an exercise by asking learners to predict the next step, place a connector, order stages, or find the faulty path before revealing the completed SVG.

## Quiz Rules

- Use a mix of prediction, multiple-choice, ordering, matching, fill-in-the-blank, short-answer, diagram, code/config completion, and “find the mistake” questions when the subject supports them.
- For multiple-choice questions, use 4 options, store the correct answer at index `0`, and deterministically shuffle options at render time using a stable seed such as the question ID.
- Ask the learner to rate confidence before revealing an answer.
- Explain why the response is right or wrong, why plausible alternatives fail, which concept was tested, and what to review next.
- Provide progressive hints before revealing the answer.
- Retry failed concepts with a different question or scenario instead of repeating the same prompt.
- Include cumulative reviews after every 2–3 modules and interleave earlier concepts with current material.
- Ground every explanation and exercise in the workshop source.

## Active Learning and Retention

Design the workshop around the loop `predict → learn → retrieve → apply → reflect`:

- Ask what the learner already knows, their role, and their goal at the beginning; use these answers to tailor examples when practical.
- Ask for a prediction before revealing an important workflow, outcome, or explanation.
- Require learners to explain key ideas in their own words through pause-and-summarize or teach-back prompts.
- Use realistic scenarios that require decisions, trade-offs, debugging, or operational reasoning.
- Let learners reconstruct sequence or architecture diagrams by ordering steps, matching components, or placing connections.
- Include compare-and-contrast exercises for concepts that are easy to confuse.
- Capture learner questions, unclear points, personal notes, and module takeaways in local progress.
- Periodically ask what remains unclear and direct the learner to targeted remediation.
- Build a personalized final review from failed answers, low-confidence answers, saved questions, and weak concepts.

Track mastery per concept rather than only slide completion:

- Use states such as `unseen`, `introduced`, `struggling`, `practicing`, `mastered`, and `due_for_review`.
- Do not mark a concept mastered after one correct answer.
- Require correct retrieval in more than one context, separated by other content.
- Prioritize confident-but-wrong and repeatedly missed concepts for remediation.
- Schedule mastered concepts for later cumulative retrieval.

Quiz records should support fields such as `id`, `conceptIds`, `type`, `prompt`, `answer`, `explanation`, `misconceptionFeedback`, `hints`, `difficulty`, `reviewAfter`, `transferPrompt`, `sourceSlides`, and `requireConfidence`. Use only fields needed by the chosen interaction types.

## ADQ Mode

If the user asks for ADQ or an architecture deep-dive style workshop:

- Focus on systems, boundaries, data flow, deployment shape, and external dependencies.
- Mention important traits, classes, topics, queues, tables, and config files.
- Prefer architecture and operational reasoning over method-by-method walkthroughs.
- Use diagrams and production-flavored examples wherever the source material supports them.

## Interactive Tutorial Mode

When the user asks for a step-by-step tutorial, interactive guide, keyword retention, or track-trace progress:

- Organize the material into 12–20 progressive steps across coherent modules when the source supports that depth.
- Highlight 2–3 important terms per slide and provide concise definitions.
- Add a meaningful retrieval or application check to each step.
- Show slide progress and concept mastery separately; include a sidebar timeline with current learning states.
- Add a glossary containing the highlighted terms.
- Build a learner-generated memory sheet from summaries, difficult concepts, saved questions, and takeaways.
- Include a one-click control that downloads the standalone HTML file.

Treat these as mode-specific enhancements, not requirements for every workshop.

## Review Checklist

Before finishing:

- Confirm the HTML is self-contained.
- Confirm the `STORE_KEY` is unique for that workshop.
- Confirm multiple-choice questions have 4 options, `answer: 0`, deterministic shuffling, and an explanation.
- Confirm every assessed concept has feedback, remediation, and a later retrieval opportunity.
- Confirm mastery requires successful retrieval in multiple contexts.
- Confirm the personalized review prioritizes mistakes and low-confidence responses.
- Confirm the slide order, TOC, and keyboard navigation are consistent.
- Confirm claims in the slides are supported by the provided source material.
