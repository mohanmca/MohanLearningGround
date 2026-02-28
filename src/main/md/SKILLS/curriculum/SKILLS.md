---
name: curriculum
description: Create or revise workshop curricula, training outlines, and full table-of-contents plans grounded in the current workspace. Use when the user wants module sequencing, slide-level TOCs, learning progression, or quiz-aware curriculum planning.
---

# Curriculum

Use this skill when the user wants a course structure, workshop outline, or detailed module-by-module TOC derived from local source material.

## Default Behavior

- Ground the curriculum in the current workspace, user notes, and any supplied source files.
- Prefer a workshop-style progression from fundamentals to implementation, operations, and best practices.
- Output a full TOC in Markdown unless the user asks for another format.
- Keep the naming and scope aligned with the actual repository domain instead of reusing examples from unrelated topics.

## Workflow

1. Inspect the available source material.
   Prioritize docs, README files, examples, package names, folders, tests, pipelines, and any existing slide or workshop artifacts.
2. Determine the course shape.
   Decide whether the material supports a short workshop, a multi-module deep dive, or a broader curriculum.
3. Draft the module sequence before filling in slide titles.
   Each module should have a clear learning goal and build on the previous ones.
4. Generate the final TOC with per-module slide titles and summary stats.
5. Cross-check that every title is supported by the workspace and that totals are internally consistent.

## Grounding Rules

- Only mention tools, products, modules, services, or internal names that are present in the workspace or provided by the user.
- If the repo does not expose a precise name, use accurate generic phrasing instead of guessing.
- Do not invent file paths, teams, acronyms, or production systems.
- Mirror existing local workshop conventions when they exist, but adapt the content to the current repository.

## Curriculum Shape

Unless the workspace strongly suggests otherwise:

- Start with foundations, terminology, and repository orientation.
- Move into core architecture, configuration, workflows, and real implementation walkthroughs.
- End with testing, operations, security, best practices, troubleshooting, or capstone material.

Default to a contiguous module sequence such as `M0` through `M11`, but adjust the count if the material is clearly narrower or broader.

## Module Design

Each module should usually include:

- a focused module title
- 2 to 10 content slides, depending on topic depth
- one final quiz slide when quizzes are part of the requested output

Early modules can be broader. Later modules should generally narrow toward practice, operations, and synthesis instead of repeating fundamentals.

## Slide Title Guidance

Prefer short, concrete, Title Case slide names tied to one concept each.

Useful patterns include:

- `What Is <Concept>?`
- `Repository Tour: <Area>`
- `<Component> Architecture`
- `<Tool> vs Alternatives`
- `<Config or Syntax> Crash Course`
- `Real Module: <Existing Module Name>`
- `Deployment Pipeline`
- `Observability & Monitoring`
- `Security & Access Control`
- `Best Practices Checklist`
- `Anti-Patterns To Avoid`
- `Testing Workflow`

Avoid filler titles such as `Introduction` when a more precise learning outcome is available.

## TOC Output

When the user asks for a full workshop TOC, prefer this structure:

1. `## REFERENCE: <Workshop Name> Full TOC (All Modules & Slides)`
2. Module sections `M0` through `MN`
3. A Markdown table per module with columns `#` and `Slide Title`
4. A final bold quiz row for each module when quizzes are included
5. `### TOC Summary Stats`

For each module header, include the content slide count and quiz count if quizzes are in scope.

## Summary Stats

When generating summary stats, include:

- total modules
- total slides
- quiz question count or quiz slide assumptions
- slide count range per module
- naming pattern used
- content density pattern across the course

Totals must match the module tables.

## Review Checklist

Before finishing:

- Confirm the curriculum reflects the current workspace domain.
- Confirm module numbering is contiguous.
- Confirm each module has enough content to justify its existence.
- Confirm slide numbering restarts within each module when using tables.
- Confirm each module ends with a quiz row when quizzes are requested.
- Confirm summary stats are consistent with the generated TOC.
