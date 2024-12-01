import App from './App.svelte';
import {register, init, getLocaleFromNavigator} from "svelte-i18n";

// register('en', () => import('./locales/en.json'));
// register('it', () => import('./locales/it.json'));
//
// init({
//     fallbackLocale: 'en',
//     initialLocale: getLocaleFromNavigator(),
// });

const app = new App({
    target: document.body,
    props: {
        name: 'world'
    }
});

export default app;