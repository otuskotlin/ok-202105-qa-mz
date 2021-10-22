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

1. [app-kafka](app-kafka) Application with Kafka as a transport.
1. [app-ktor](app-ktor) Ktor application
1. [services](services) Service layer
1. [common](common) Inner models
1. [common-cor](common-cor) DSL library used to implement Chain of Responsibility pattern
1. [docker](docker) Docker-compose configurations to run applications in containers   
1. [logics](logics) Business logic
1. [model-stubs](model-stubs) Stubs for inner models
1. [rest](rest) Test HTTP requests
1. [specs](specs) Open API service description
1. [transport-mapping](transport-mapping) Mapping between transport and inner models
1. [transport-openapi](transport-openapi) Transport models (generated with OpenAPI)
1. [transport-openapi-stubs](transport-openapi-stubs) Stubs for transport models
1. [validation](validation) Request validators
