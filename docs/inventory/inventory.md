# Inventory and level

## Inventory - functions

- display content of equipment
- shows infromation about items in specific slots
- moving items around slots in the equipment
- gaining/ removing items from certain slots

## Items

- contains name, description, value

## Level

- displays users' current experience value

- increases users' experience after completing a task

## DB:

Stores information about users' inventory content and infromation about their

level and experience points

- Inventory

  - inventory_id (PK)
  - user_id (FK)
  - slot_1_item_id (FK)

    .

    .

    .

  - slot_10_item_id (FK)

- Item

  - item_id (PK)
  - name
  - description
  - value

- Level

  - level_id (PK)
  - user_id (FK)
  - level_value
  - current_experience
  - exp_to_next_lvl

## Object:

- Inventory

  - getInventory()
  - deleteItem()
  - addItem(index)
  - getItem(index)
  - move(index1\_, index2)
  - getItemInfo()
  - getInventoryValue()

- Item

  - createItem()
  - deleteItem()
  - setItemName()
  - getItemName()
  - setDescription()
  - getDescription()
  - setValue()
  - getValue()

- Level

  - setLevel()
  - getLevel()
  - setExperience()
  - getExperience()
  - setExpToNextLvl()
  - getExpToNextLvl()

**Information for Architects: We want to communicate with users, market and fight modules**
