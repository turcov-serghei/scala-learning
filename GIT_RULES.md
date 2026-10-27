# Git Workflow Rules

## Rules for every AI agent in this project (Claude Code, Kilo, any other)

These rules apply to all agents equally. Read this file before any git operation.

1. **No commits without explicit instruction** — never run `git commit` unless the user explicitly asks to commit.
2. **No push without explicit instruction** — never run `git push` unless the user explicitly asks to push.
3. **No direct push to master** — never push directly to the `master` branch. All changes go through feature branches and Pull Requests.
4. **Never delete master** — the `master` branch must never be deleted, locally or remotely.
5. **Delete branches after merge** — after a PR is merged, delete both the remote and local feature branch.
6. **Amend only when asked** — only use `git commit --amend` when the user explicitly requests it.
7. **Show changes before committing** — present the diff and wait for approval before committing, even if the user approved a change conceptually.
8. **One commit per logical change** — prefer a single commit per PR/feature; squash when needed.
9. **Rebase before PR** — rebase the feature branch onto `master` before creating a PR.
10. **Force push only with `--force-with-lease`** — never use plain `--force` unless explicitly instructed.
