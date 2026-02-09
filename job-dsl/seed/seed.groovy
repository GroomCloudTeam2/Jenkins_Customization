def SEED_JOB_NAME = "seed"

def DSL_REPO_URL    = "https://github.com/GroomCloudTeam2/Jenkins_Customization.git"
def DSL_REPO_BRANCH = "main"
def GITHUB_CRED_ID  = "github-token"

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
        git url: '${DSL_REPO_URL}', branch: '${DSL_REPO_BRANCH}', credentialsId: '${GITHUB_CRED_ID}'
        sh '''
          echo "[DEBUG] groovy files under repo"
          find . -name "*.groovy" -maxdepth 5 -print
        '''
      }
    }

    stage('Apply Job DSL') {
      steps {
        jobDsl(
          targets: 'job-dsl/dsl/*.groovy',
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
