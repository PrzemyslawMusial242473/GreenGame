import { createRouter, createWebHistory } from 'vue-router'
import Home from './components/Home.vue'
import ScoreboardTable from './components/ScoreboardTable.vue'
import AppFight from './components/AppFight.vue'
import InventoryTable from './components/InventoryTable.vue'
import Chat from './components/Chat.vue'
import FriendList from './components/FriendList.vue'
import DailyPopUp from './components/DailyPopUp.vue'
const routes = [
    { path: '/', component: Home },
    { path: '/fight', component: AppFight },
    { path: '/scoreboard', component: ScoreboardTable },
    { path: '/inventory', component: InventoryTable },
    { path: '/chat', component: Chat },
    { path: '/friends', component: FriendList },
    { path:'/daily',component: DailyPopUp }
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router