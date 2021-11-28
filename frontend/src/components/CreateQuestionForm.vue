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
      <textarea rows = "30" cols="200" name="content" v-model="question.content">Question</textarea>
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

export default {
  name: "CreateQuestion",
  data(){
    return {
      question: {
        title: null,
        content: null
      }
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