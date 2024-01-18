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
      <div id="healthBar" :style="{width: this.HPBar + '%'}">
      </div>
    </div>
    <div id="score">
      <h1>Score: {{ this.score }}</h1>
    </div>
    <div id="rubbish"/>
  </div>
</template>

<script>
let HP = 0;
import axios from "../../axios.js";
export default {
  name: "FightEnvironment",
  data() {
    return {
      HPBar: HP * 33,
      score: 0,
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
        getHP()
        {
          axios.get("http://localhost:8080/secured/fight/HP").then(response =>
          {
            this.HPBar = response.data * 33;
          })
        }
      },
  mounted() {
    this.emitter.on('correctAnswer', () => {
      this.score++;
    })

    this.emitter.on('wrongAnswer', () => {
      this.HPBar -= 33;
      if (this.HPBar < 33) {
        alert('You lost!')
        window.location.reload();
      }
    })

    this.emitter.on('callForScore', () => {
      this.emitter.emit('shareScore', {score: this.score})
    })

    this.emitter.on('avatar-selected', (avatar) => {
      this.selectedAvatar = avatar;
      this.getHP();
    })

    this.changeEnemyHead();
    setInterval(this.changeEnemyHead, 2000);
  },
};
</script>

<style scoped>
template {
  display: flex;
}

#env {
  width: 100%;
  height: 879px;
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
  bottom: 25%;
  left: 0%;
  width: 37%;
  height: 45%;
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
  bottom: -52%;
  left: 50%;
  width: 15%;
  height: 22%;
}

</style>