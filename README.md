
# Chatbot powered by OpenAI API

A simple implementation of chatbot that utilizes OpenAI API. This application contains necessary class for AWS Lambda deployment.




## Environment Variables

To run this project locally, you will need to add the following environment variables.

`OPENAI_API_KEY` - API Key that you can retrieve from your OpenAI account

`AUTH_API_KEY` - Your app API Key, you need to create this since this will be utilized by the basic security authentication through security filter chain

`AI_CONTEXT` - The context of your AI. You need this if you intend to run this application locally.

`ORIGIN_1` - The origin url of where you expect the request to come from. You can also modify the .yml to add multiple origins. This is needed for CORS related configuration.


## Run Locally

You need to make sure that all the environment variables mentioned above is correctly set-up. The profile should also be 'dev' so that it will load the intended yml config.

```
  -Dspring.profiles.active=dev
```


## Deployment

To deploy this project to AWS Lambda. You need to pass the AI_CONTEXT as parameter to `mvn clean package`

```
  mvn clean package -DAI_CONTEXT="You chatbot AI context"
```

Once the jar file is generated, just upload it to AWS Lambda.
Do not forget to setup the environment variable on AWS Lambda configuration section.

You also need to setup some lambda specific setting like:

- `MAIN_CLASS` environment variable
- `Handler` - should be pointed to your handleRequest method

You can reference this guide:
https://aws.amazon.com/blogs/compute/re-platforming-java-applications-using-the-updated-aws-serverless-java-container/

You should also configure your own AWS API Gateway and point the POST method to your newly created AWS Lambda function.