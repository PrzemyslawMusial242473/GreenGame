<!-- App.vue -->
<template>
  <div id="app" class="container">
    <!--<ScoreboardTable /> -->
    <Chat />
  </div>
</template>

<script>
import "bootstrap/dist/css/bootstrap.min.css";
import FriendList from "./components/FriendList.vue";
import ScoreboardTable from "./components/ScoreboardTable.vue";
import Chat from "./components/Chat.vue";

export default {
  components: {
    FriendList,
    ScoreboardTable,
    Chat,
  },
};
</script>

<style>
/* Add your global styles here */
</style>
