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

## Output Naming

Use topic-based names unless the user specifies otherwise.

- HTML: `{topic_name}_workshop.html` or `{topic_name}.html`
- Quiz JSON: `{topic_name}_quiz.json`

If the user requests revisions to an existing workshop, preserve the current naming and update only what is necessary.
