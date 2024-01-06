<template>
  <div>
    <FightEnvironment/>
    <QuizQuestion/>
  </div>
</template>

<script>
import FightEnvironment from "@/components/FightEnvironment.vue";
import QuizQuestion from "@/components/QuizQuestion.vue";

export default {
  name: 'App',
  components: {
    QuizQuestion,
    FightEnvironment,
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
