# DSync
A simple, lightweight, customisable minecraft spigot plugin for pushing player events to a discord webhook.

## Usage

Firstly, download the `.jar` file from Releases. Place this file into the plugins folder and restart the server.

On Discord, enter the channel settings on the text channel that you would like the events pushed to. On the integrations tab, 
create a webhook and give it any name and profile image that you would like. Copy the webhook URL.

Back on your server, open the file `plugins/DSync/config.yml` and paste the URL inside the quotation marks next
to `webhook_url:`. So for example, the line should look like:

    webhook_url: 'https://discord.com/api/webhooks/38221871/jE_29emEJdn29NSodnz0'

with the URL being different.

Lastly, restart your server again and the changes will take effect.

### Customising Messages

Messages can be edited in the `DSync/config.yml` file. Special tokens can be used in order to replace parts of the preset with event variables.

Event messages and their respective tokens can be found in [this documentation file](docs/messages.md)
