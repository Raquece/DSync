# Messages and their Respective Tokens

### `player.join`
A player joined the game.

Tokens:
- `$PLAYER` - The player that has joined.

### `player.quit`
A player left the game.

Tokens:
- `$PLAYER` - The player that has left.

### `player.death`
A player has died.

Tokens:
- `$MESSAGE` - The death message shown in chat.
- `$VICTIM` - The player that has died.

### `player.advancement`
A player has unlocked an advancement.

Tokens:
- `$PLAYER` - The player that has unlocked the advancement.
- `$ADVANCEMENT` - The title of the advancement

### `server.started`
Embeded message. The server or plugin has started.

Parameters:
- `title` - The title of the embed.
- `description` - The body of the embed.
- `color` - The colored highlight of the embed in integer format.

### `server.stopped`
Embeded message. The server or plugin has stopped.

Parameters:
- `title` - The title of the embed.
- `description` - The body of the embed.
- `color` - The colored highlight of the embed in integer format.
