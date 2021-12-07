<template>
  <link rel="stylesheet" href="/navbar.css">
  <header>
    <nav id="navbar">
      <ul>
        <li><img src="/winter_trees.png" alt="Logo" width="20px;" height="20px;"></li>
        <li><a href="/">Home</a></li>
        <li><a href="/question/list">View Questions</a></li>
        <li><a href="/create_question">Create Question</a></li>
        <li id="login-button"><a href="/login">Login</a></li>
      </ul>
    </nav>
  </header>
  <main>
    <div>
      <h1>Create Question</h1>
      <notifications width='400px' ignore-duplicates="true"/>
      <form @submit="postForm" method="post">
        <label>
          Author
          <input type="text" name="author" placeholder="Question Author" v-model="question.author">
        </label>
        <br> <br>
        <label>
          Title
          <input type="text" name="title" placeholder="Question Title" v-model="question.title">
        </label>
        <br> <br>
        Question
        <br> <br>
        <textarea rows = "20" cols="100" name="content" v-model="question.content">Question</textarea>
        <br> <br>
        <button type="submit">Save</button>
      </form>
    </div>
  </main>
</template>

<script>

export default {
  name: "CreateQuestion",
  data() {
    const question = {
      author: null,
      title: null,
      content: null,
    };
    return {
      question: question
    }
  },
  methods: {
    postForm(event) {
      const request = {
        question: this.question,
        className: "CreateQuestionRequest"
      }
      this.axios
          .post("question/create", request)
          .then((result) => {
            if (result.data.result === "success") {
              this.$notify({
                type: "success",
                text: "Stored.",
                duration: 1000
              })
            } else {
              const errors = result.data.errors;
              this.$notify({
                type: "error",
                text: JSON.stringify(errors),
                width: '300px',
                duration: -1
              })
            }
          })
      event.preventDefault();
    }
  }
}
</script>

<style scoped>
</style>