---
description: Information on the PlayerData class
---

# PlayerData

## Getting the DataStore

PlayerData getters and setters are all handled via the [DataStoreHandle](https://github.com/harry0198/MailMe/blob/master/src/main/java/com/haroldstudios/mailme/datastore/DataStoreHandler.java)r

```
DataStoreHandler dataStore = mailMe.getDataStoreHandler();
```

{% hint style="warning" %}
 It's recommended to only store one MailMe instance
{% endhint %}

Once you have your DataStoreHandler instance, you can use the handler to get [PlayerData](https://github.com/harry0198/MailMe/blob/master/src/main/java/com/haroldstudios/mailme/datastore/PlayerData.java)

{% api-method method="get" host="https://github.com/harry0198/MailMe/blob/master/src/main/java/com/haroldstudios/mailme" path="/datastore/PlayerData.java" %}
{% api-method-summary %}
Getting the PlayerData
{% endapi-method-summary %}

{% api-method-description %}
Gets the PlayerData of a provided player
{% endapi-method-description %}

{% api-method-spec %}
{% api-method-request %}
{% api-method-path-parameters %}
{% api-method-parameter name="Player" type="string" required=false %}
Provide the Player
{% endapi-method-parameter %}

{% api-method-parameter name="Uuid" type="object" required=false %}
Provide the Player's UUID
{% endapi-method-parameter %}
{% endapi-method-path-parameters %}
{% endapi-method-request %}

{% api-method-response %}
{% api-method-response-example httpCode=200 %}
{% api-method-response-example-description %}
Usage:
{% endapi-method-response-example-description %}

```
# Using the Player's Name
PlayerData playerData = dataStore.getPlayerData(player);
# Using the Player's Name
PlayerData playerData = dataStore.getPlayerData(player);
```
{% endapi-method-response-example %}
{% endapi-method-response %}
{% endapi-method-spec %}
{% endapi-method %}

## PlayerData Class

I will be going over a few methods briefly, all of the methods are documented in their entirety [here](https://github.com/harry0198/MailMe/blob/master/src/main/java/com/haroldstudios/mailme/datastore/PlayerData.java)

{% api-method method="get" host="https://github.com/harry0198/MailMe/blob/master/src/main/java/com/haroldstudios/mailme" path="/datastore/PlayerData.java" %}
{% api-method-summary %}
Sending Mail
{% endapi-method-summary %}

{% api-method-description %}
This method sends a mail object to a player
{% endapi-method-description %}

{% api-method-spec %}
{% api-method-request %}
{% api-method-path-parameters %}
{% api-method-parameter name="Mail" type="object" required=true %}
A Mail Object
{% endapi-method-parameter %}
{% endapi-method-path-parameters %}
{% endapi-method-request %}

{% api-method-response %}
{% api-method-response-example httpCode=200 %}
{% api-method-response-example-description %}
Usage:
{% endapi-method-response-example-description %}

```
PlayerData playerData = dataStore.getPlayerData(player);
Mail mail = # Mail Object
playerData.addMail(mail);
```
{% endapi-method-response-example %}
{% endapi-method-response %}
{% endapi-method-spec %}
{% endapi-method %}

{% api-method method="get" host="https://github.com/harry0198/MailMe/blob/master/src/main/java/com/haroldstudios/mailme" path="/datastore/PlayerData.class" %}
{% api-method-summary %}
Get Mail
{% endapi-method-summary %}

{% api-method-description %}
Returns a List of the player's Mail
{% endapi-method-description %}

{% api-method-spec %}
{% api-method-request %}
{% api-method-path-parameters %}
{% api-method-parameter name="No Parameters!" type="string" required=false %}
No parameters required!
{% endapi-method-parameter %}
{% endapi-method-path-parameters %}
{% endapi-method-request %}

{% api-method-response %}
{% api-method-response-example httpCode=200 %}
{% api-method-response-example-description %}
Usage
{% endapi-method-response-example-description %}

```
PlayerData playerData = dataStore.getPlayerData(player);
Mail mail = # Mail Object
playerData.getMail();
```
{% endapi-method-response-example %}
{% endapi-method-response %}
{% endapi-method-spec %}
{% endapi-method %}

{% api-method method="get" host="https://github.com/harry0198/MailMe/blob/master/src/main/java/com/haroldstudios/mailme" path="/datastore/PlayerData.class" %}
{% api-method-summary %}
Update the player's PlayerData.json file
{% endapi-method-summary %}

{% api-method-description %}
This saves the changes to file and is done on exit for most playerdata methods however, if you alter something yourself differently this method will need to be ran
{% endapi-method-description %}

{% api-method-spec %}
{% api-method-request %}
{% api-method-path-parameters %}
{% api-method-parameter name="No Parameters!" type="string" required=false %}
No Parameters required!
{% endapi-method-parameter %}
{% endapi-method-path-parameters %}
{% endapi-method-request %}

{% api-method-response %}
{% api-method-response-example httpCode=200 %}
{% api-method-response-example-description %}
Usage
{% endapi-method-response-example-description %}

```
PlayerData playerData = dataStore.getPlayerData(player);
Mail mail = # Mail Object
playerData.update();
```
{% endapi-method-response-example %}
{% endapi-method-response %}
{% endapi-method-spec %}
{% endapi-method %}

