// jc_folders.groovy랑 동일한 파일명
def ROOT_FOLDER = "monorepo"

// GitHub 정보
def GITHUB_OWNER   = "yyytgf123"
def GITHUB_REPO    = "test_repo"
def GITHUB_CRED_ID = "github-token"

// "monorepo/jc-root" 라는 폴더 안에 멀티브랜치 파이프라인 Job(jc-root) 을 만든다는 뜻
multibranchPipelineJob("${ROOT_FOLDER}/jc-root") {

    // pipeline&folder 명
    displayName("e_commerce_v3 (root)")
    description("Root multibranch: ${GITHUB_OWNER}/${GITHUB_REPO} -> Jenkinsfile")

    // 멀티브랜치가 스캔할 "브랜치 소스"를 정의
    branchSources {
        branchSource {
            source {
                github {
                    // GitHub 소스의 내부 식별자, Jenkins가 소스를 구분할 때 쓰는 "키" 같은 개념
                    // Job 이름이나 DSL을 재적용해도 같은 소스로 인식시키기 위함
                    id("jc-root-${GITHUB_OWNER}-${GITHUB_REPO}".replaceAll('[^A-Za-z0-9_.-]', '_'))
                    repoOwner(GITHUB_OWNER)
                    repository(GITHUB_REPO)
                    credentialsId(GITHUB_CRED_ID)

                    // 플러그인이 업데이트되면서 없으면 error
                    repositoryUrl("https://github.com/${GITHUB_OWNER}/${GITHUB_REPO}")

                    // false : repoOwner() + repository() 조합이 진자 설정 소스
                    // true : repositoryUrl() 값이 진자 설정 소스
                    configuredByUrl(false)

                    // GitHub Branch Source 플러그인에서 제공하는 "스캔/필터/발견 규칙" 모음
                    // 즉, 브랜치 소스를 어떻게 탐색할지 설정 제공
                    traits {
                        gitHubBranchDiscovery { strategyId(3) }
                    }
                }
            }
        }
    }

    factory {
        workflowBranchProjectFactory {
            scriptPath("Jenkinsfile")
        }
    }

    orphanedItemStrategy {
        discardOldItems {
            numToKeep(30)
        }
    }

    // 웹훅 없으면 주기 스캔 켜두는 게 편함
    triggers {
        periodicFolderTrigger { interval("1h") }
    }
}
