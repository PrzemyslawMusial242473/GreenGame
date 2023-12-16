<template>
  <div id="pop-up" v-if="this.showPopup">
    <h1>{{this.question.question}}</h1>
  </div>
</template>

<script>
import json from '../../public/question.json'
export default {
  name: "QuizQuestion",
  data() {
    return {
      showPopup: false,
      question: json
    };
  },
  mounted() {
    this.emitter.on('showQuestions', () => {
        this.showPopup = true;
    })
  }
}
</script>

<style scoped>
#pop-up{
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
}

</style>