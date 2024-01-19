<template>
  <div id="env">
    <div id="player">
      <div id="playerBody">

      </div>
      <div id="playerHead">
        <img :src="selectedAvatar">
      </div>
      <div id="playerWeapon">

      </div>
    </div>
    <div id="enemy" v-on:click="showQuestions">
      <div id="enemyBody">

      </div>
      <div id="enemyHead">
        <img :src="chosenEnemyHead">
      </div>
      <div id="enemyWeapon">

      </div>
    </div>
    <div id="HP">
      <h1>HP</h1>
      <div id="healthBar">
      </div>
    </div>
    <div id="score">
      <h1>Score: {{ this.score }}</h1>
    </div>
    <div id="rubbish"/>
  </div>
</template>

<script>
import axios from "../../axios.js";

export default {
  name: "FightEnvironment",
  data() {
    return {
      HPBar: 100,
      HP: 0,
      remainingHP: 0,
      ID: 0,
      score: 0,
      total: 0,
      selectedAvatar: "",
      chosenEnemyHead: "",
      enemyHeads: [
        require('@/assets/enemyhead1.png'),
        require('@/assets/enemyhead1-1.png')
      ]
    };
  },
  methods:
      {
        showQuestions() {
          this.emitter.emit('showQuestions')
        },
        changeEnemyHead() {
          const randomIndex = Math.floor(Math.random() * this.enemyHeads.length);
          this.chosenEnemyHead = this.enemyHeads[randomIndex];
        },
        getHP() {
          axios.get("http://localhost:8080/secured/fight/HP").then(response => {
            console.log("HP: ", response.data);
            this.HP = response.data;
            this.remainingHP = response.data;
          })
        },
        getID() {
          axios.get("http://localhost:8080/secured/fight/ID").then(response => {
            console.log("ID: ", response.data);
            this.ID = response.data;
          })
        },
      },
  mounted() {
    this.emitter.on('correctAnswer', () => {
      this.score++;
    })

    this.emitter.on('wrongAnswer', () => {
      this.HPBar -= (100 / this.HP);
      this.remainingHP--;

      console.log("Current HPbar: ", this.HPBar);
      const healthBar = document.getElementById('healthBar');

      healthBar.style.width = this.HPBar + '%';
      if (this.HPBar < (100 / this.HP) - 1) {
        healthBar.style.width = '0%';

        let fightData = {points: this.score, numberOfQuestions: this.total, hp: 0};
        console.error('Score: ', this.score);
        console.error('Remaining HP: ', this.remainingHP);

        axios.post("http://localhost:8080/secured/scoreboard", fightData).then(response =>
        {
          console.log('Response: ', response);
        }).catch(error =>
        {
          console.error('Error: ', error);
        });

        setTimeout(function () {
          alert('You lost!');
          window.location.reload();
        }, 500);
      }
    })

    this.emitter.on('callForScore', (data) => {
      this.total = data.total;

      this.emitter.emit('shareScore', {score: this.score, hp: this.remainingHP});
    })

    this.emitter.on('avatar-selected', (avatar) => {
      this.selectedAvatar = avatar;
      this.getHP();
      this.getID();
    })

    this.changeEnemyHead();
    setInterval(this.changeEnemyHead, 2000);
  },
};
</script>

<style scoped>
template {
  display: flex;
  overflow: hidden;
}

#env {
  width: 100%;
  height: 737px;
  background-image: url('~@/assets/forest.jpg');
  background-size: cover;
  background-attachment: fixed;
}

#player {
  position: absolute;
  bottom: 10%;
  left: 25%;
  width: 20%;
  height: 30%;
}

#enemy {
  position: absolute;
  bottom: 10%;
  left: 60%;
  width: 20%;
  height: 30%;
}

#playerBody {
  background-image: url("~@/assets/body.png");
  position: absolute;
  width: 75%;
  height: 100%;
}

#enemyBody {
  background-image: url("~@/assets/body.png");
  position: absolute;
  width: 75%;
  height: 100%;
}

#playerHead {
  position: absolute;
  bottom: 48%;
  width: 50%;
  left: 20%;
  height: 65%;
}

#enemyHead {
  background-image: url("~@/assets/enemyhead1-1.png");
  position: absolute;
  bottom: 68%;
  width: 42%;
  left: 13%;
  height: 65%;
}

#playerWeapon {
  background-image: url("~@/assets/brick.png");
  position: absolute;
  bottom: 18%;
  left: 0%;
  width: 46%;
  height: 53%;
}

#HP {
  position: absolute;
  display: flex;
  margin-left: 1%;
  text-align: center;
  color: crimson;
  width: 30%;
  height: 20%;
  flex-direction: column;
}

#healthBar {
  position: relative;
  background-color: crimson;
  border-radius: 15px;
  height: 25%;
}

#score {
  display: flex;
  position: absolute;
  color: antiquewhite;
  text-align: start;
  margin-top: 9%;
  margin-left: 1%;
}

#rubbish {
  background-image: url("~@/assets/rubbish.png");
  position: relative;
  bottom: -62%;
  left: 46%;
  width: 15%;
  height: 25%;
}

</style>