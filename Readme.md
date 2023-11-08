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

Create a `HomeController.java` class next to `DemoApplication.java`:

Populate it with the following code:

```java
package com.example.demo;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class HomeController {

    @GetMapping("/")
    public String home(@AuthenticationPrincipal OidcUser user) {
        return "Hello, " + user.getFullName() + "!";
    }
}
```

### Add a home page and logout feature (Optional)

See [this commit](https://github.com/oktadev/auth0-spring-boot-passkeys-demo/commit/7071b7ff15cee4a0517d4c7645004d3674186b78) for the code.

### Create an Auth0 account

Sign up for a free [Auth0 Account](https://auth0.com/signup). If you have an existing account, [create a new Tenant](https://auth0.com/docs/get-started/auth0-overview/create-tenants).

### Enable passkeys on your Auth0 tenant

1. Log in to your [Auth0 Dashboard](https://manage.auth0.com) and navigate to **Authentication** > **Database** > **Username-Password-Authentication**.
   1. If the second tab says **Authentication Methods**, your tenant supports passkeys, proceed to the next step.
   2. If the second tab says **Password Policy**, your tenant doesn't support passkeys, [Create a new tenant](https://auth0.com/docs/get-started/auth0-overview/create-tenants) and proceed to the next step.
2. Navigate to **Authentication** > **Authentication Profile** and select **Identifier First**. **Save** your changes.
3. Navigate to **Authentication** > **Database** > **Username-Password-Authentication** and select the **Authentication Methods** tab and enable **Passkey**.

### Install the Auth0 CLI

```shell
# Using Homebrew on macOS and Linux
brew tap auth0/auth0-cli && brew install auth0

# ---- OR ----
# Download the binary using cURL. Binary will be downloaded to current folder as "./auth0".
curl -sSfL https://raw.githubusercontent.com/auth0/auth0-cli/main/install.sh | sh -s -- -b .
# To be able to run the binary from any directory, make sure you move it to a place in your $PATH
sudo mv ./auth0 /usr/local/bin
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
  --description "Spring Boot passkeys Demo" \
  --type regular \
  --callbacks http://localhost:8080/login/oauth2/code/okta \
  --logout-urls http://localhost:8080 \
  --reveal-secrets
```

Note the domain, client ID and client secret.

### Start the app and sign up with passkeys

Update the domain, client ID and client secret to an `application.properties` file on the root of the app.

```
okta.oauth2.issuer=https://<AUTH0_domain>/
okta.oauth2.client-id=<AUTH0_clientId>
okta.oauth2.client-secret=<AUTH0_clientSecret>
```

Start the app.

```shell
./gradlew bootRun
```

Visit [http://localhost:8080]. Click on **Sign Up** and create a new user. You will be prompted to sign up with a passkey.
