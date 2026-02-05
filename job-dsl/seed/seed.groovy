def SEED_JOB_NAME = "seed"

// DSL을 담고 있는 레포(= Jenkins_Customization)
def DSL_REPO_URL   = "https://github.com/yyytgf123/test_repo2.git"
def DSL_REPO_BRANCH = "main"

// Jenkins에 등록된 GitHub PAT 크리덴셜 ID
def GITHUB_CRED_ID = "github-token"

// Seed Pipeline Job 생성
pipelineJob(SEED_JOB_NAME) {
    description("Seed job: applies Job DSL scripts under job-dsl/dsl/**")

    definition {
        cps {
            sandbox(true)
            script("""
pipeline {
  agent any

  stages {
    stage('Checkout DSL Repo') {
      steps {
        script {
          git url: '${DSL_REPO_URL}', branch: '${DSL_REPO_BRANCH}', credentialsId: '${GITHUB_CRED_ID}'
        }
      }
    }

    stage('Apply Job DSL') {
      steps {
        jobDsl(
          targets: 'jenkins-customization/job-dsl/dsl/*.groovy',
          sandbox: false,
          lookupStrategy: 'JENKINS_ROOT',
          removedJobAction: 'DELETE',
          removedViewAction: 'DELETE'
        )
      }
    }
  }
}
""")
        }
    }
}
