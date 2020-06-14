import { combineReducers } from 'redux';
import { reducer as formReducer } from 'redux-form';

import indexReducer from './indexReducer';
import searchReducer from './searchReducer';
import topicReducer from './topicReducer';
import documentReducer from './documentReducer';
import languageReducer from './languageReducer';

export default combineReducers({
    language: languageReducer,
    index: indexReducer,
    search: searchReducer,
    topic: topicReducer,
    document: documentReducer,
    form: formReducer
});
