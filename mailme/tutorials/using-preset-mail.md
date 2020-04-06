---
description: This page explains how to create and send preset mails! Including welcome mail
---

# Using Preset Mail

## Creating Preset Mail

Creating preset mail is very similar to creating and sending regular mail.

1. Execute the command /MailMe admin preset &lt;unique-name&gt;
2. Follow the onscreen steps
3. Done! The plugin will send you a confirmation mail to double check it's what you'd like!

```text
/MailMe admin preset <unique-name>
```

##   Setting a Welcome Mail

Setting a welcome mail is very simple. Simply execute the following command and follow the onscreen steps \(see Creating Preset Mail\)

```text
/MailMe admin preset welcome
```

## Sending Preset Mail

Simply execute the following command where unique name is the name of the preset mail:

```text
/MailMe admin send <all/player-name> [unique-name]
```

## Updating the Contents of a Preset Mail

You cannot currently directly line by line update the contents of the mail. However, you can overwrite previous mail by executing the following command with the same unique-name as the one you'd like to overwrite:

```text
/MailMe admin preset <unique-name>
```

