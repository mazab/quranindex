import topics from '../apis/topics';
import {
    FETCH_INDEX,
    DO_SEARCH,
    FETCH_TOPIC,
    FETCH_DOCUMENT,
    SET_LANGUAGE
} from './types';

export const fetchIndex = () => async (dispatch, getState) => {
    if (getState().index.length > 0) {
        dispatch({ type: FETCH_INDEX, payload: getState().index});
    } else {
        const response = await topics.get('/topics');
        dispatch({ type: FETCH_INDEX, payload: response.data});
    }
};

export const doSearch = (search_query) => async (dispatch, getState) => {
    const query = search_query.trim();
    if (getState().search[query]) {
        dispatch({ type: DO_SEARCH, payload: { query: query, result: getState().search[query] }});
    } else {
        const languge = getState().language;
        const response = await topics.get(`/topics/search/${languge}`, {
            params: { value: query }
        });
        dispatch({ type: DO_SEARCH, payload: { query: query, result: response.data}});
    }
};

export const fetchTopic = topicId => async (dispatch, getState) => {
    if (getState().topic[topicId]) {
        dispatch({ type: FETCH_TOPIC, payload: getState().topic[topicId]});
    } else {
        const response = await topics.get(`topics/${topicId}`);
        dispatch({ type: FETCH_TOPIC, payload: response.data });
    }
};

export const fetchDocument = (parentId, ayah, surah) => async (dispatch, getState) => {
    const documentId = `${surah}_${ayah}`;
    if (getState().document[documentId] && getState().topic[parentId]) {
        dispatch({ type: FETCH_DOCUMENT, payload: getState().document[documentId]});
    } else {
        const response = await topics.get(parentId == '0' ? `entries/${surah}/${ayah}` : `entries/${parentId}/${surah}/${ayah}`);
        if (response.data.parent) {
            dispatch({ type: FETCH_TOPIC, payload: response.data.parent });
        }
        const document =  response.data.entry ?  response.data.entry :  response.data
        dispatch({ type: FETCH_DOCUMENT, payload: { documentId: documentId, document: document } });
    }
};

export const setLanguage = language => {
    return {
        type: SET_LANGUAGE, 
        payload: language 
    }
};
