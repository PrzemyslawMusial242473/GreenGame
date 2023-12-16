<template>
  <div id="pop-up" v-if="this.showPopup">
    <h1>{{ this.question.question }}</h1>
    <div class="options" id="options">
      <div class="firstButtons" id="firstButtons">
        <button id="b1" v-on:click="check(1)" v-text="this.currentAnswers[0]"></button>
        <button id="b2" v-on:click="check(2)" v-text="this.currentAnswers[1]"></button>
      </div>
      <div class="secondButtons" id="secondButtons">
        <button id="b3" v-on:click="check(3)" v-text="this.currentAnswers[2]"></button>
        <button id="b4" v-on:click="check(4)" v-text="this.currentAnswers[3]"></button>
      </div>
    </div>
  </div>
</template>

<script>
import json from '../../public/question.json'

export default {
  name: "QuizQuestion",
  data() {
    return {
      showPopup: false,
      question: json,
      currentAnswers: [json.answer1Correct, json.answer2, json.answer3, json.answer4],
      correctAnswer: json.answer1Correct,
      total: 1,
      time: 15,
      timeout: setTimeout(this.check, this.time * 1000, 0),
    };
  },
  mounted() {
    this.emitter.on('showQuestions', () => {
      this.showPopup = true;
    })
  },
  methods:
      {
        check(choice) {
          if (choice == this.correctAnswer) {
            this.emitter.emit('correctAnswer')
          } else {
            this.emitter.emit('wrongAnswer')
          }
        }
      }
}
</script>

<style scoped>
#pop-up {
  top: 20%;
  left: 15%;
  position: fixed;
  margin: auto;
  display: flex;
  height: 75%;
  width: 75%;
  background-color: rgba(0, 0, 0, 0.5);
  border-radius: 20px;
  align-items: center;
  justify-content: center;
  flex-direction: column;
}

#options {
  margin-top: 3%;
}

button {
  left: 10%;
  width: 400px;
  height: 200px;
  top: 150px;
  font-size: 120%;
  margin-left: 1px;
  border-radius: 15px;
}

h1 {
  color: azure;
}

</style>