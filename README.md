[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)

# For the [Kotlin Backend Developer](https://otus.ru/lessons/kotlin/) course

# Opinion Service 

The web service allows any user to publish a question with predefined list of answers.
Other users may select answers from the list and view statistics of selections.
Display questions ordered by time or count of answers.

# Further Development

The Opinion service is a web service that does not help you to resolve problems,
instead it allows you to know what people think about some common questions
related to everyday life, habits, personal preferences, beliefs, traditions, life values.

The key features of the service:
- open-source
- respect user privacy
- free to use, self-moderated, and community-driven - like 
  [StackOverflow](https://stackoverflow.com/) and [Wikipedia](https://www.wikipedia.org/)
- able to analise collected information and allow build statistics based on
wide set of parameters - answer time, country, age, gender, language, etc.
In that the service reminds survey services similar with [SurveyMonkey](https://www.surveymonkey.com/),
and [Typeform](https://www.typeform.com/).  
- can be used as a kind of online voting system 
  (examples: https://www.capterra.com/voting-software/, https://transmitter.ieee.org/makerproject/view/91d35)

Such service may be interesting for ordinary people, because sharing common values is important for humans.
Also it may be interesting for social researchers, psychologists, philosophers, politicians, marketers, etc.
The obvious problem with such service is how to motivate people for participation.
I do not know, but I found a successful example - the [TruePublic](https://truepublic.com/) 
is very close to the desired service. 


# Subprojects

1. [backend/apps/kafka](backend/apps/kafka) Application with Kafka as a transport.
1. [backend/apps/ktor](backend/apps/ktor) Ktor application.
1. [backend/services](backend/services) Service layer.
1. [backend/models](backend/models) Inner models, and their stubs.
1. [backend/dsl/cor](backend/dsl/cor) DSL library used to implement Chain of Responsibility pattern
1. [docker](backend/docker) Docker-compose configurations to run applications in containers   
1. [backend/logics](backend/logics) Business logic
1. [multiplatform/rest](multiplatform/rest) Test HTTP requests
1. [multiplatform/specs](multiplatform/specs) Open API service description
1. [backend/transport/mapping](backend/transport/mapping) Mapping between transport and inner models
1. [backend/transport/openapi](backend/transport/openapi) Transport models (generated with OpenAPI), and their stubs
1. [backend/validation](backend/validation) Request validators
