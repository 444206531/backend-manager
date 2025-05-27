<template>
  <AppLayout />
</template>

<script setup lang="ts">
import AppLayout from './layouts/AppLayout.vue' 
// AppLayout will be auto-imported by unplugin-vue-components if it's placed in src/layouts
// and if 'layouts' is included in the component scan paths (by default it might only scan src/components).
// For safety, explicit import is better here.
// If auto-import is set up for 'src/layouts', this explicit import might not be needed.
// Let's assume explicit import is safer for now.
</script>

<style>
/*
  Global styles like body margin, font-family, and default color
  have been moved to src/assets/main.css.
  Keep any #app specific styles here if needed, or styles tightly coupled
  to App.vue's direct template elements.
  If this style block is empty, it can be removed.
*/

/* Example of an #app specific style if you had one:
#app {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}
*/
</style>
