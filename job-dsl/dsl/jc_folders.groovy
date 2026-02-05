def ROOT_FOLDER = "monorepo"
def SERVICES_FOLDER = "services"

// Jenkins에 Folder Item 생성
folder(ROOT_FOLDER) {
    displayName("monorepo")
    description("Auto-generated folder for monorepo jobs (Job DSL)")
}

// 하위 폴더로 e_commerce_v2/services 생성
folder("${ROOT_FOLDER}/${SERVICES_FOLDER}") {
    displayName("Services")
    description("Auto-generated folder for service multibranch jobs (Job DSL)")
}
