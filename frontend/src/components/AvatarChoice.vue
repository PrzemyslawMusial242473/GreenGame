<template>
  <div id="pop-up" v-if="showPopup">
    <h1>Choose your avatar</h1>
    <div id="pictures">
      <img src="../assets/jordanHead.png" @click="selectAvatar('jordanHead')">
      <img src="../assets/meme.png" @click="selectAvatar('meme')">
      <img src="../assets/dogde.png" @click="selectAvatar('dogde')">
    </div>
  </div>
</template>

<script>
export default {
  name: "AvatarChoice",
  data() {
    return {
      showPopup: true,
      selectedAvatar: "",
    };
  },
  methods: {
    selectAvatar(avatar) {
      this.selectedAvatar = require('@/assets/' + avatar + '.png');
      this.showPopup = false;
      this.emitter.emit('avatar-selected', this.selectedAvatar);
    },
  },
};
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
  flex-direction: column;
}

h1 {
  color: aliceblue;
  font-size: 300%;
}

#pictures{
  display: flex;
  margin-top: 12%;
}

img{
  margin: auto;
}
</style>