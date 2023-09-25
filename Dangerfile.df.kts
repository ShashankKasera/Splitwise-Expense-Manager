import systems.danger.kotlin.*
//import java.io.File

danger(args) {
    val createdFiles = git.createdFiles
    val modifiedFiled = git.modifiedFiles
    val allSourceFiles = modifiedFiled + createdFiles
    val bigPRThreshold = 1200
    val prTitleRegex = Regex("^((#\\d+)(,\\s+)*)+:\\s+.*\$")

    onGitHub {
        val prAdditionCount = pullRequest.additions ?: 0
        val prDeletionCount = pullRequest.deletions ?: 0

        //check ReleaseNotes.md
        if (!allSourceFiles.contains("ReleaseNotes.md")) {
            fail("No ReleaseNotes.md found in this PR. Please ensure that you add release notes in ReleaseNotes.md file.")
        }

        //check jiraId in PR title
        if (!prTitleRegex.matches(pullRequest.title)) {
            fail("No Github issue Id found in PR Title. Please ensure that the PR title conforms to the format: `<Github_issue_Id>: <Title>`.")
        }

        //check changes should be less than the threshold
        if (prAdditionCount + prDeletionCount > bigPRThreshold) {
            warn("Pull request contains heavy changes. Please ensure that your PR is small for faster review.")
        }

        //check assignee
        if (pullRequest.assignees.isNullOrEmpty()) {
            message("Let's also assign this pull request to someone for review.")
        }

        //check description/body
        if (pullRequest.body.isNullOrEmpty()) {
            warn("Having a proper description on PR gives more clarity to the reviewer. Please ensure that the PR has a neatly written description explaining the purpose of this change request.")
        }

        //check non lineary commit history
        var hasNonLinearHistory = false
        commits?.forEach { it ->
            if (it.commit.parents?.count() ?: 0 > 0) {
                hasNonLinearHistory = true
            }
        }
        if (hasNonLinearHistory) {
            warn("Pull request contains non linear commit history. Please ensure that the PR does not have any merge commit. Prefer rebase over merge.")
        }
    }

    onGit {
        createdFiles.filter {
            it.endsWith(".java")
        }.forEach {
            warn("Still living in legacy! Please consider developing any new feature in Kotlin.", it, 1)
        }
    }
}