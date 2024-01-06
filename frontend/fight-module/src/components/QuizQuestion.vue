<template>
  <div id="pop-up" v-if="this.showPopup">
    <h1>{{ this.question.question }}</h1>
    <div id="countdown-number" v-text="this.time"></div>
    <div class="options" id="options">
      <div class="firstButtons" id="firstButtons">
        <button id="b1" v-on:click="questionsHandler(this.currentAnswers[0])" v-text="this.currentAnswers[0]"
                :style="{borderColor: this.answered ? 'black' : (this.currentAnswers[0] === this.correctAnswer ? 'green' : 'red')}"
                :disabled="!this.answered"></button>
        <button id="b2" v-on:click="questionsHandler(this.currentAnswers[1])" v-text="this.currentAnswers[1]"
                :style="{borderColor: this.answered ? 'black' : (this.currentAnswers[1] === this.correctAnswer ? 'green' : 'red')}"
                :disabled="!this.answered"></button>
      </div>
      <div class="secondButtons" id="secondButtons">
        <button id="b3" v-on:click="questionsHandler(this.currentAnswers[2])" v-text="this.currentAnswers[2]"
                :style="{borderColor: this.answered ? 'black' : (this.currentAnswers[2] === this.correctAnswer ? 'green' : 'red')}"
                :disabled="!this.answered"></button>
        <button id="b4" v-on:click="questionsHandler(this.currentAnswers[3])" v-text="this.currentAnswers[3]"
                :style="{borderColor: this.answered ? 'black' : (this.currentAnswers[3] === this.correctAnswer ? 'green' : 'red')}"
                :disabled="!this.answered"></button>
      </div>
    </div>
  </div>
</template>

<script>
import json from '../../public/questions.json'

let step = 0;
const time = 15;
export default {
  name: "QuizQuestion",
  data() {
    return {
      score: 0,
      showPopup: false,
      question: json[step],
      currentAnswers: [json[step].answer1Correct, json[step].answer2, json[step].answer3, json[step].answer4],
      correctAnswer: json[step].answer1Correct,
      total: 2,
      time: time,
      timeout: null,
      answered: true,
      timer: null,
    };
  },
  mounted() {
    this.emitter.on('showQuestions', () => {
      for (let i = this.currentAnswers.length - 1; i > 0; i--) {
        let randomIndex = Math.floor(Math.random() * (i + 1));
        [this.currentAnswers[i], this.currentAnswers[randomIndex]] = [this.currentAnswers[randomIndex], this.currentAnswers[i]]
      }
      this.showPopup = true;
      clearTimeout(this.timeout);
      this.timeout = setTimeout(this.questionsHandler, time * 1000, '');
      this.timer = setInterval( () => {
        --this.time;
      }, 1000);
    })
    this.emitter.on('shareScore', (data) => {
      this.score = data.score;
    })
  },
  methods:
      {
        check(choice) {
          if (choice === this.correctAnswer) {
            this.emitter.emit('correctAnswer')
          } else {
            this.emitter.emit('wrongAnswer')
          }
        },
        async questionsHandler(choice) {
          if(this.time === 1)
          {
            this.time = 0;
          }
          clearTimeout(this.timer);
          clearTimeout(this.timeout);
          this.check(choice);
          this.answered = !this.answered;
          await new Promise(resolve => setTimeout(resolve, time * 100));
          if (step + 1 === this.total) {
            this.emitter.emit('callForScore');
            alert('Your score is ' + this.score);
            window.location.reload();
          } else {
            step++;
            this.answered = !this.answered;
            this.question = json[step]
            this.currentAnswers = [json[step].answer1Correct, json[step].answer2, json[step].answer3, json[step].answer4]
            this.correctAnswer = json[step].answer1Correct;
            for (let i = this.currentAnswers.length - 1; i > 0; i--) {
              let randomIndex = Math.floor(Math.random() * (i + 1));
              [this.currentAnswers[i], this.currentAnswers[randomIndex]] = [this.currentAnswers[randomIndex], this.currentAnswers[i]]
            }
            this.time = time;
            this.timeout = setTimeout(this.questionsHandler, time * 1000, '');
            this.timer = setInterval( () => {
              --this.time;
            }, 1000);
          }
        },
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
  margin-left: 3px;
  border-radius: 15px;
  border-width: 5px;
}

button:disabled {
  background-color: white;
  color: black;
}

h1 {
  color: azure;
}

#countdown-number {
  position: relative;
  font-size: 225%;
  color: azure;
  align-self: end;
  margin-right: 5%;
  margin-top: -5%;
}


@keyframes countdown {
  from {
    stroke-dashoffset: 0px;
  }
  to {
    stroke-dashoffset: 314px;
  }
}

</style>