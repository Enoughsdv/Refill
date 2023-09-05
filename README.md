# Refill

A super basic refill code that is based with signs

### Config

```yml
SIGN_SETTINGS:
  COOLDOWN_OPTIONS:
    SECONDS: 20
    IN_COOLDOWN_MESSAGE: "&cYou are currently in cooldown, wait %time% seconds."
  LINES:
    - "&r"
    - "&3&lRefill"
    - "&7(Right click)"
    - "&r"
  LINE_TO_SET: "[REFILL]"

MESSAGES:
  NO_PERMISSIONS: "&cYou have no permissions"
  BREAK_FAILED: "&cYou cannot break this sign"
  BREAK_SUCCESSFULLY: "&aYou have successfully removed a refill sign."

INVENTORY_SETTINGS:
  OPTIONS:
    SIZE: 54
    TITLE: "&3&lRefill Menu"
  FILLER:
    # Soup refill example
    # MATERIAL: MUSHROOM_SOUP
    # DATA: 0
    MATERIAL: POTION
    DATA: 16421
    AMOUNT: 1
  ITEMS:
    #ID:DATA | MATERIAL, AMOUNT, SLOT
    - 373:8226, 1, 36
    - 373:8226, 1, 37
    - 373:8226, 1, 38
    - 373:8227, 1, 40
    - 373:8226, 1, 42
    - 373:8226, 1, 43
    - 373:8226, 1, 44
    - 283:0, 1, 45
    - 283:0, 1, 46
    - 283:0, 1, 47
    - 283:0, 1, 48
    - 368:0, 16, 49
    - 283:0, 1, 50
    - 283:0, 1, 51
    - 283:0, 1, 52
    - 283:0, 1, 53
```
