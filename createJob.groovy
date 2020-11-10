pipelineJob('home-job') {
    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url 'https://github.com/AlexGolovko/Smart-Home.git'
                    }
                    branch 'master'
                }
            }
        }
    }
}