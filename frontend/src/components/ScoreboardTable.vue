<template>
    <div class="scoreboard">
        <!-- FOR TESTING -->
        <!-- <form>
            <input v-model="this.userEmail" type="text" name="userEmail" placeholder="Email">
            <input v-model="this.points" type="number" name="points" placeholder="Points">

            <button type="button" @click="addScore(this.points)">Add Score</button>
            <button type="button" @click="getScoreboard()">Get Scoreboard</button>
        </form> -->
        <h1>Scoreboard</h1>
        <div>
            <button @click="sortAsc">Sort asc</button>
            <button @click="sortDesc">Sort desc</button>
        </div>
        <form class="m-2">
            <input for="limitButton" v-model="this.limit" type="number" name="limit" placeholder="Limit">
            <button name="limitButton" type="button" @click="getTopScores(this.limit)">Get top Scoreboard</button>
            <button type="button" @click="getScoresByFriends()">Get friends Scoreboard</button>
            <button type="button" @click="getUserScore()">Get user score</button>
        </form>
        <table class="table table-light table-striped table-hover table-bordered">
            <thead>
                <tr>
                    <th>Rank</th>
                    <th>User</th>
                    <th>Points</th>
                    <th>Number of games</th>
                    <th>Achievements</th>
                </tr>
            </thead>
            <tbody class="table-group-divider">
                <tr v-for="score, index in this.scores" :key="score.rank">
                    <th scope="row">{{ score.rank }}</th>
                    <td>{{ score.username }}</td>
                    <td>{{ score.points }}</td>
                    <td>{{ score.numberOfGames }}</td>
                    <td>
                        <p v-for="achievement in score.achievements" :key="achievement.name">
                            {{ achievement.achievementName }}
                        </p>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>
<script>
import axios from '../api/axios.js'

export default {
    name: 'ScoreboardTable',
    data() {
        return {
            userEmail: '',
            // points: 0,
            limit: 0,
            scores: [],
        }
    },
    mounted() {
        this.getScoreboard();
    },
    methods: {
        sortAsc() {
            this.scores.sort((a, b) => a.rank - b.rank);
        },
        sortDesc() {
            this.scores.sort((a, b) => b.rank - a.rank);
        },
        getScoreboard() {
            axios.get('/scoreboard')
            .then(response => {
                    console.log(response.request.responseURL);
                    if (response.status === 403) {
                        window.location.href = "localhost:8080/login";
                        return;
                    }
                    if (response.request.responseURL.includes("login")) {
                        console.log("Redirected to login page:", response.request.responseURL);
                        window.location.href = response.request.responseURL;
                        return Promise.reject(new Error("Redirected to login"));
                    }
                    console.log(response.data);
                    this.scores = response.data;
                })
                .catch(error => {
                    console.log(error);
                });
        },
        getTopScores(limit) {
            axios.get('/scoreboard/top/' + limit)
                .then(response => {
                    if (response.status === 403) {
                        window.location.href = "localhost:8080/login";
                        return;
                    }
                    if (response.request.responseURL.includes("login")) {
                        console.log("Redirected to login page:", response.request.responseURL);
                        window.location.href = response.request.responseURL;
                        return Promise.reject(new Error("Redirected to login"));
                    }
                    console.log(response);
                    console.log(response.data);
                    this.scores = response.data;
                })
                .catch(error => {
                    console.log(error);
                });
        },
        getScoresByFriends() {
            axios.get('/scoreboard/friends')
                .then(response => {
                    if (response.status === 403) {
                        window.location.href = "localhost:8080/login";
                        return;
                    }
                    if (response.request.responseURL.includes("login")) {
                        console.log("Redirected to login page:", response.request.responseURL);
                        window.location.href = response.request.responseURL;
                        return Promise.reject(new Error("Redirected to login"));
                    }
                    console.log(response);
                    console.log(response.data);
                    this.scores = response.data;
                })
                .catch(error => {
                    console.log(error);
                });
        },
        getUserScore() {
            axios.get('/scoreboard/user')
                .then(response => {
                    if (response.status === 403) {
                        window.location.href = "localhost:8080/login";
                        return;
                    }
                    if (response.request.responseURL.includes("login")) {
                        console.log("Redirected to login page:", response.request.responseURL);
                        window.location.href = response.request.responseURL;
                        return Promise.reject(new Error("Redirected to login"));
                    }
                    console.log(response);
                    console.log(response.data);
                })
                .catch(error => {
                    console.log(error);
                });
        },
        // async addScore(points) {
        //     let req = JSON.stringify({
        //         points: points,
        //         numberOfQuestions: 5, //TODO: change this
        //         hp: 100,
        //     });
        //     console.log(req);
        //     axios.post('/scoreboard', req)
        //         .then(response => {
        //             if (response.status === 403) {
        //                 window.location.href = "localhost:8080/login";
        //                 return;
        //             }
        //             if (response.request.responseURL.includes("login")) {
        //                 console.log("Redirected to login page:", response.request.responseURL);
        //                 window.location.href = response.request.responseURL;
        //                 return Promise.reject(new Error("Redirected to login"));
        //             }
        //             console.log(response);
        //             console.log(response.data);
        //             this.getScoreboard();
        //         }).catch(error => {
        //             console.log(error);
        //         });
        // }
    }
}
</script>