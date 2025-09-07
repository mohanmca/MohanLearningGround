## Claude Usages
1. You can interact with Cluad like another developer before implementing
2. You can improver user-memory by asking lesson learnt
3. Create your own Claude commands to improve your workflow
4. You can have different perceiption by keep CLAUDE.md under each folder, admin-tools/CLAUDE.md, internal-tools/CLAUDE.md can have different context for claude
5. you can have ~/CLAUDE.md and give commands that you often use and context about project that you work often
6. Ask Claude to do only what were asked to do, don't involve in other activities
7. Ask claude to explain "What it is going to do?"
8. 

## Claude Commands and prompts
1. ```claude --resume```
2. *#* I forget to complete xyz, please remind me if we work through my calendary block. (Project memory, project memory local, user memory
3. '/forwar-slash to bring claude commands'
4. ```/compact``` -- summarize and clear context and keep only summary
5. bash commands to execute, "! echo mohan" "!pwd"
6. ```Ask Claude to use git ls-files isntead of find command when searching files```
7. ```/permissions command```
8. You can ask claude to read the PR review feedback and fix it
9. You can ask Claude to address all the feedback
10. 


## Effective usage of claude
1. Use plan mode before aking claude to do actual coding/change
2. Ask Claude to explain
    1. Please explain the functionality and code you just built out in detail. Walk me through we\hat you changed and how it works. Act like you’re a senior engineer teaching me code
4. Ask feedback about how to use it effectively
5. Try not to loose the claude context
6. Ensure plan of work is linearized and granular (Claude Code, for example, will manage a ‘checklist’, but you can save a plan file)

## Before closing claude
1. Hey you learned a lot, what are all the things that you learnt and patterns that you learnt that should be remembered going forward
2.  


## Use claude code to answer questions about your codebase
1. How is @RoutingController.py used
2. How do I make a new @app/services/ValidationTemplateFactory?
3. Why does recoverFromException take so many arguments? Look through git history to answer
4. Why did we fix issue #1882 by addding if/else in @src/login.ts API?
5. In which version did we release the new @api/ext/PreHooks.php API?
6. Look at PR #92929, then carefully verify which app versions were impacted
7. What dis we ship yesterday
8. Use 3 parallel agents to brainstorm ideas for how to clean up @services/aggregator/feed_serice.cpp
9. Figure out the root cause for issue @92292, Let me choose an approach before you code. Ultrathink
10. 

# Claude commands
1. commit, push, pr
2. You are a GitHub code contribution analyst.Your task is to:1. **Identify all contributions** related to the  DAILY_USD_RATE  by a specific "user-id"

## 10 useful claude memory related tips
Here are 10 practical tips and tricks from GitHub and developer blogs for getting the most out of Claude Code's agentic tools—with a special focus on memory preservation and supporting continuous learning from human feedback:

1. **Curate a CLAUDE.md File (Per Project/Folder)**  
   Create and regularly update a `CLAUDE.md` in your repo and even in subdirectories. Use it to give Claude detailed, persistent instructions, clarify architecture, describe tests, and define agent behaviors. This file becomes agent memory the model refers to and improves context retention[1][2][3].

2. **Use Persistent Memory Plugins/Tools**  
   Leverage open-source tools like "Basic Memory" (for session notes in Markdown) or "MCP Memory Keeper" (SQLite-backed persistent context server) to maintain project memory across coding sessions. These tools let you, or Claude, store evolving context, key decisions, requirements, and ideas completely outside session limits, acting as a local vault for long-term context[4][5].

3. **Document All Claude's Reasoning**  
   Regularly prompt Claude to output its intermediate thoughts, plans, and decisions to markdown documents—not just code. Later, both you and Claude can reference these docs to regain context, track decision history, and facilitate continuous learning[2].

4. **Explicitly Prompt Extended Thinking/Multi-Step Reasoning**  
   Use prompt extensions like `think`, `think harder`, or `ultrathink` to tell Claude to dedicate more computation (token budget) and deeper reasoning to harder problems—useful for debugging, architecture, or learning new frameworks. This helps build better tacit context and increases transparency for human review[2][6].

5. **Iterative Testing and Commit-Driven Learning**  
   Direct Claude with workflows like:  
   - Write tests → Commit  
   - Write code that passes tests → Commit  
   - Repeat and review  
   Instruct Claude not to “hallucinate” implementations before tests pass, and engage it in multiple cycles—each time, its memory of the codebase and test suite improves[7].

6. **Project-Specific Slash Commands**  
   Configure `.claude/commands` scripts for common/recurring project operations and context-loading tasks. These improve continuity, automate context recall, and let human developers teach Claude more about your team’s conventions[2].

7. **Visuals as Memory Anchors**  
   Drag and drop screenshots or diagrams into Claude Code. The system can remember and reason about UI, bug states, or requirements much better when visuals are present and referenced in docs, making future context richer for both AI and human learners[2].

8. **Actively Curate Knowledge Graphs**  
   Tools like “Basic Memory” can auto-create and expand project knowledge graphs as you interact with Claude. Proactively update these graphs with new entities/concepts so both you and Claude have growing, interconnected context over time[4].

9. **Leverage Local File System for Context**  
   Because Claude Code relies on file access, you can instruct it to keep running notes in ancillary `.md` or `.txt` files—design docs, meeting notes, known issues—as a persistent agentic memory[3].

10. **Regularly Review, Edit, and Prune Memory**  
   Memory files can become stale or unwieldy. Incorporate regular human review: prune outdated data, clarify ambiguous notes, and summarize learning/guidance from lengthy sessions for Claude to draw on later[2][6].

These practices let agentic tools like Claude Code build, maintain, and actually learn from persistent, structured memory—with continuous, guided improvement from human developers acting as both teacher and editor[7][1][2][4][3][6][5].

[1] https://www.reddit.com/r/ClaudeAI/comments/1k5slll/anthropics_guide_to_claude_code_best_practices/
[2] https://bagerbach.com/blog/how-i-use-claude-code/
[3] https://grantslatton.com/claude-code
[4] https://www.reddit.com/r/ClaudeAI/comments/1jdga7v/basic_memory_a_tool_that_gives_claude_persistent/
[5] https://lobehub.com/mcp/mkreyman-mcp-memory-keeper
[6] https://stefanbauschard.substack.com/p/claude-4-memory-tools-and-the-evolution
[7] https://www.anthropic.com/engineering/claude-code-best-practices
[8] https://www.youtube.com/watch?v=wFQ_i9XdkXU
[9] https://natesnewsletter.substack.com/p/the-claude-code-complete-guide-learn
[10] https://beam.ai/llm/claude/
[11] https://www.anthropic.com/news/claude-4
[12] https://www.youtube.com/watch?v=TiNpzxoBPz0
[13] https://claude.ai
[14] https://simonwillison.net/2025/Apr/19/claude-code-best-practices/
[15] https://www.anthropic.com/engineering/built-multi-agent-research-system
[16] https://sajalsharma.com/posts/effective-ai-coding/
[17] https://claude.ai/public/artifacts/b872435b-1d9c-461e-a29c-b03d252053a0
[18] https://www.descope.com/blog/post/claude-vs-chatgpt
[19] https://aws.amazon.com/blogs/aws/agents-for-amazon-bedrock-now-support-memory-retention-and-code-interpretation-preview/
[20] https://smythos.com/developers/tool-usage/create-ai-agents-using-claude/

## Reference
1. [Master Claude Code in 14 Minutes (8 Steps)](https://www.youtube.com/watch?v=cjW6ofe7AY4)
2. [Mastering Claude Code in 30 minutes](https://www.youtube.com/watch?v=6eBSHbLKuN0&t=12s)
3. https://www.anthropic.com/engineering/claude-code-best-practices
4. https://harper.blog/2025/05/08/basic-claude-code/
5. https://harper.blog/2025/02/16/my-llm-codegen-workflow-atm/
