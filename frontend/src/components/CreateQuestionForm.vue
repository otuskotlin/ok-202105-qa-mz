<template>
  <div>
    <h1>Create Question</h1>
    <form @submit="postForm" method="post">
      <label>
        Question Title
        <input type="text" name="title" placeholder="Question Title" v-model="question.title">
      </label>
      <br> <br>
      Question
      <br> <br>
      <textarea rows = "20" cols="100" name="content" v-model="question.content">Question</textarea>
      <br> <br>
      <label>
        Question Tags
        <select multiple>
          <option>Meal</option>
          <option>Sport</option>
          <option>Science</option>
        </select>
      </label>
      <br> <br>
      <button type="submit">Save</button>
    </form>
  </div>

</template>

<script>
import {ru} from "transport-models/opinion-multiplatform";

export default {
  name: "CreateQuestion",
  data() {
    const question = new ru.otus.opinion.common.models.Test("", "");
    return {
      question: question
    }
  },
  methods: {
    postForm(event) {
      this.axios
          .post("question/create", this.question)
          .then((result) => {
            console.log(result)
          })
      console.log(this.question)
      event.preventDefault();
    }
  }
}
</script>

<style scoped>

</style>