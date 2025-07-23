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

## Reference
1. [Master Claude Code in 14 Minutes (8 Steps)](https://www.youtube.com/watch?v=cjW6ofe7AY4)
2. [Mastering Claude Code in 30 minutes](https://www.youtube.com/watch?v=6eBSHbLKuN0&t=12s)
3. https://www.anthropic.com/engineering/claude-code-best-practices
4. https://harper.blog/2025/05/08/basic-claude-code/
5. https://harper.blog/2025/02/16/my-llm-codegen-workflow-atm/
