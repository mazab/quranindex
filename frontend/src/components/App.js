import React from 'react';
import { useSelector } from 'react-redux';
import { Router, Route, Switch } from 'react-router-dom';
import {
    FacebookShareButton,
    LinkedinShareButton,
    RedditShareButton,
    TelegramShareButton,
    TwitterShareButton,
    WhatsappShareButton
  } from "react-share";

import NavBar from './NavBar';
import Index from './Index';
import Topic from './Topic';
import Document from './Document';
import SearchResults from './SearchResults';
import history from '../history';
import style from './App.module.css';

const App = () => {
    const language = useSelector(state => state.language);
    return (<Router history={history}>
        <div className="ui container">
                <div>
                    <NavBar />
                    <div className={`ui one cards ${style.container} ${language === 'ar' ? 'rtl' : 'ltr'}`}>
                        <Switch>
                            <Route path="/" exact component={Index} />
                            <Route path="/topic/:id" exact component={Topic} />
                            <Route path="/document/:id" exact component={Document} />
                            <Route path="/search/:query" exact component={SearchResults} />
                        </Switch>
                    </div>
                </div>
            
        </div>
        <div className="ui inverted vertical footer segment">
            <div className="ui container">
                <div className="ui stackable inverted divided equal height stackable grid">
                    <div className="five wide column">
                        <h4 className="ui inverted header">Share</h4>
                        <div className="ui inverted">
                            <FacebookShareButton className="ui icon button" url="https://quranindex.org">
                                <i className="facebook icon"></i>
                            </FacebookShareButton>
                            <TwitterShareButton className="ui icon button" url="https://quranindex.org">
                                <i className="twitter icon"></i>
                            </TwitterShareButton>
                            <LinkedinShareButton className="ui icon button" url="https://quranindex.org">
                                <i className="linkedin icon"></i>
                            </LinkedinShareButton>
                            <RedditShareButton className="ui icon button" url="https://quranindex.org">
                                <i className="reddit icon"></i>
                            </RedditShareButton>
                            <TelegramShareButton className="ui icon button" url="https://quranindex.org">
                                <i className="telegram icon"></i>
                            </TelegramShareButton>
                            <WhatsappShareButton className="ui icon button" url="https://quranindex.org">
                                <i className="whatsapp icon"></i>
                            </WhatsappShareButton>
                        </div>
                    </div>
                    <div className="three wide column">
                        <h4 className="ui inverted header">Contact Us</h4>
                        <div className="ui inverted link list">
                            <a className="item" href = "mailto: info@quranindex.org">Send us an e-mail</a>
                            <a className="item" target="_blank" rel="noopener noreferrer" href = "https://github.com/mazab/quranindex">Developers resources</a>
                        </div>
                    </div>
                    <div className="three wide column">
                        <h4 className="ui inverted header">© QuranIndex 2020</h4>
                        <div className="ui inverted link list">
                            <a className="item" href="https://quranindex.org">All Rights Reserved</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </Router>);
};

export default App;