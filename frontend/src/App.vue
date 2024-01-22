<!-- AppFight.vue -->
<template>
  <div id="app">
    <div class="bg-white">
      <ul class="nav nav-tabs">
        <li class="nav-item">
          <router-link class="nav-link" to="/">Home</router-link>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" to="/fight">Fight</router-link>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" to="/inventory">Inventory</router-link>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" to="/scoreboard">Scoreboard</router-link>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" to="/chat">Chat</router-link>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" to="/friends">Friends</router-link>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="http://localhost:8080/market/allOffers">Market</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="http://localhost:8080/secured/hello">Profile</a>
        </li>
      </ul>
    </div>
    <router-view ></router-view>
    <!-- <FightApp />
    <ScoreboardTable />
    <Chat />
    <FriendList />
    <InventoryApp />
    <ScoreboardTable /> -->
  </div>
</template>

<script>
import "bootstrap/dist/css/bootstrap.min.css";
import FriendList from "./components/FriendList.vue";
import ScoreboardTable from "./components/ScoreboardTable.vue";
import Chat from "./components/Chat.vue";
import FightApp from "./components/AppFight.vue";
import InventoryApp from "./components/AppInventory.vue";
import { RouterView } from "vue-router";

export default {
  components: {
    FriendList,
    ScoreboardTable,
    Chat,
    FightApp,
    InventoryApp,
    RouterView
},
};
</script>

<style>
/* Add your global styles here */
</style>
