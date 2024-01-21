<!-- AppFight.vue -->
<template>
  <div id="app" class="container">
    <FightApp />
    <ScoreboardTable />
    <Chat />
    <FriendList />
    <InventoryApp />
    <ScoreboardTable />
  </div>
</template>

<script>
import "bootstrap/dist/css/bootstrap.min.css";
import FriendList from "./components/FriendList.vue";
import ScoreboardTable from "./components/ScoreboardTable.vue";
import Chat from "./components/Chat.vue";
import FightApp from "./components/AppFight.vue";
import InventoryApp from "./components/AppInventory.vue";

export default {
  components: {
    FriendList,
    ScoreboardTable,
    Chat,
    FightApp,
    InventoryApp,
  },
};
</script>

<style>
/* Add your global styles here */
</style>
