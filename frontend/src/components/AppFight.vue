<template>
  <div>
    <FightEnvironment/>
    <QuizQuestion/>
    <AvatarChoice/>
  </div>
</template>

<script>
import FightEnvironment from "./FightEnvironment.vue";
import QuizQuestion from "./QuizQuestion.vue";
import AvatarChoice from "./AvatarChoice.vue";

export default {
  name: 'App',
  components: {
    QuizQuestion,
    FightEnvironment,
    AvatarChoice,
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
