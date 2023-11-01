# Passkeys with Spring Boot and Auth0 by Okta

This is a bare-bones Spring Boot web application configured to use the [Okta Spring Boot Starter](https://github.com/okta/okta-spring-boot) which auto-configures OIDC authentication with Spring Security.

Auth0 by Okta is used as the IdP to demonstrate the passkeys feature for passwordless login.

## Why use passkeys?

Passkeys are FIDO credentials that are discoverable by browsers or housed in hardware authenticators like your mobile device, laptop, or security keys for passwordless authentication. Passkeys replace passwords with cryptographic key pairs for phishing-resistant sign-in security and an improved user experience. The cryptographic keys are used from end-user devices (computers, phones, or security keys) for user authentication. Any passwordless FIDO credential is a passkey.

We believe that passkeys offer a viable alternative to passwords for consumer applications, and we are committed to promoting this much-needed industry shift by making it easy for you, developers, and builders to offer that experience to your users.

### Create a Spring Boot App

```shell
curl -G https://start.spring.io/starter.tgz \
  -d dependencies=web,okta -d baseDir=passkey-demo | tar -xzvf -
```

### Add a home page and logout feature (Optional)

See [this commit](https://github.com/oktadev/auth0-spring-boot-passkeys-demo/commit/7071b7ff15cee4a0517d4c7645004d3674186b78) for the code.

### Create an Auth0 account

Sign up for a free [Auth0 Account](https://auth0.com/signup). If you have an existing account, [create a new Tenant](https://auth0.com/docs/get-started/auth0-overview/create-tenants).

### Enable passkeys for your Auth0 tenant

1. Log in to your [Auth0 Dashboard](https://manage.auth0.com) and navigate to **Authentication** > **Database** > **Username-Password-Authentication**.
2. If the second tab says **Password Policy**, go to step 3. If the second tab says **Authentication Methods**, you have passkeys enabled. Click on **Authentication Methods** tab and enable **Passkey**.
3. [Create a new tenant](https://auth0.com/docs/get-started/auth0-overview/create-tenants), then navigate to **Authentication** > **Authentication Profile** and select **Identifer First**. **Save** your changes. Now go to step 2.

### Install the Auth0 CLI

```shell
brew tap auth0/auth0-cli && brew install auth0
```

Other [installation](https://developer.auth0.com/resources/labs/tools/auth0-cli-basics#lab-setup) options are also available.

### Create an Auth0 app

Login to the Auth0 CLI and create an Auth0 app.

```shell
# Login to the tenant
auth0 login

# Create an Auth0 app
auth0 apps create \
  --name "Spring Boot Passkeys" \
  --description "Spring Boot Example" \
  --type regular \
  --callbacks http://localhost:8080/login/oauth2/code/okta \
  --logout-urls http://localhost:8080 \
  --reveal-secrets
```

Note the domain, client ID and client secret.

### Start the app and sign up with passkeys

Update the domain, client ID and client secret to an `application.properties` file on the root of the app.

Start the app.

```shell
./gradlew bootRun
```

Visit [http://localhost:8080]. Click on **Sign Up** and create a new user. You will be prompted to sign up with a passkey.
