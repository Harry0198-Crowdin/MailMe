---
description: List of all commands and their usage
---

# Commands

## **Player Commands**

{% hint style="info" %}
All Player Permissions start with "MailMe.base."
{% endhint %}

### Reading Mail

You can read mail using one of two methods:

1. By Command:

{% hint style="info" %}
Permission: MailMe.base.read
{% endhint %}

```java
/MailMe read
```

   2. By MailBox

Simply clicking on the MailBox will open the read menu. See a tutorial on how to setup [here](https://wiki.haroldstudios.com/mailme/tutorials/mailboxes)

### Sending Mail

You can send mail by executing the following command

{% hint style="info" %}
Permission: MailMe.base.send
{% endhint %}

```java
/MailMe send
```

### Setting a MailBox

You can set your own mailbox using the following command. See an in-depth tutorial [here](https://wiki.haroldstudios.com/mailme/tutorials/mailboxes#setting-a-mailbox)

{% hint style="info" %}
Permission: MailMe.base.mailbox
{% endhint %}

```java
/MailMe mailbox set
```

### Removing a MailBox

You can remove a mailbox using a similar command to setting one. Just with one alterage:

{% hint style="info" %}
Permission: MailMe.base.mailbox
{% endhint %}

```java
/MailMe mailbox remove
```

### Setting a Language

You can set your own personal language preference! 

{% hint style="info" %}
Permission: MailMe.base.lang
{% endhint %}

```java
/MailMe lang [LANGUAGE-PREFIX]
```

### Altering Notification Setting

You can also stop receiving notifications when you receive a mail!

{% hint style="info" %}
Permission: MailMe.base.notify
{% endhint %}

```java
/MailMe notify <true/false>
```

### Read Mail as Text

If GUIs aren't your thing, you can read mail in text form with hoverable menus!

{% hint style="info" %}
Permission: MailMe.base.text
{% endhint %}

```java
/MailMe text
```

## Admin Commands

{% hint style="info" %}
All Admin Permissions are: "MailMe.ADMIN"
{% endhint %}

### Setting a default mailbox:

While looking at a CHEST, input the command:

```java
/mailme defaultMB
```

{% hint style="danger" %}
 This command sets NEW players' default mailbox. Not existing. Therefore, if a player has joined the server since the plugin being installed - it will not update their mailbox
{% endhint %}

### Presets

Presets allow for mail to be sent at a later date or to a wider array of players.

### Creating a Preset

To create a preset, you will need to input the following command with a UNIQUE id. The ID is the parameter you will need to send it at a later date

```java
/mailme admin preset <ID>
```

After this, just follow the GUI prompt on screen and you will be sent the mail to test it's how you want it to be produced.

{% hint style="success" %}
These values will then be printed into your config.yml. **It's important you don't touch these values unless you fully understand what you're doing!**
{% endhint %}

### Sending a Preset

```java
/mailme admin send <all/player> [ID]
```

This command can be executed via console provided you include the last parameter ID

**You can also send a global mail by omitting the ID and creating one there.**

\*\*\*\*

