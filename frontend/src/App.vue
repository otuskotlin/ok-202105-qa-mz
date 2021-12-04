<template>
  <CreateQuestion/>
</template>

<script>
import CreateQuestion from "@/components/CreateQuestionForm";

export default {
  name: 'App',
  components: {
    CreateQuestion,
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
  margin-top: 60px;
}
</style>
