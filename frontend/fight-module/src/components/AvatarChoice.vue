<template>
  <div id="pop-up" v-if="showPopup">
    <h1>Choose your avatar</h1>
    <div id="pictures">
      <img src="~@/assets/jordanHead.png">
      <img src="~@/assets/meme.png">
      <img src="~@/assets/dogde.png">
    </div>
  </div>
</template>

<script>
export default {
  name: "AvatarChoice",
  data() {
    return {
      showPopup: true,
    };
  },
}
</script>

<style scoped>
#pop-up {
  top: 10%;
  left: 10%;
  position: fixed;
  margin: auto;
  display: flex;
  height: 80%;
  width: 80%;
  background-color: rgba(0, 0, 0, 0.8);
  border-radius: 20px;
  align-items: center;
  flex-direction: column;
}

h1 {
  color: aliceblue;
  font-size: 300%;
}
</style>