<template>
  <div id="env">
    <div id="player">
      <div id="playerBody">

      </div>
      <div id="playerHead">

      </div>
      <div id="playerWeapon">

      </div>
    </div>
    <div id="enemy" v-on:click="showQuestions">
      <div id="enemyBody">

      </div>
      <div id="enemyHead">

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
  </div>
</template>

<script>
export default {
  name: "FightEnvironment",
  data() {
    return {
      HPBar: 125,
      score: 0,
    };
  },
  methods:
      {
        showQuestions() {
          this.emitter.emit('showQuestions')
        }
      },
  mounted() {
    this.emitter.on('correctAnswer', () => {
      this.score++;
    })
    this.emitter.on('wrongAnswer', () => {
      this.HPBar -= 33;
    })
    this.emitter.on('callForScore', () =>
    {
      this.emitter.emit('shareScore', {score: this.score})
    })
  },
};
</script>

<style scoped>
template {
  display: flex;
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
  background-image: url("~@/assets/stud00.png");
  position: absolute;
  bottom: 50%;
  width: 50%;
  left: 17%;
  height: 65%;
}

#enemyHead {
  background-image: url("~@/assets/stud11.png");
  position: absolute;
  bottom: 50%;
  width: 50%;
  left: 17%;
  height: 65%;
}

#playerWeapon {
  background-image: url("~@/assets/brick.png");
  position: absolute;
  bottom: 35%;
  left: -17%;
  width: 100%;
  height: 100%;
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

</style>