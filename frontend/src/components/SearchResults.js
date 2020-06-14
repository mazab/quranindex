import React, { Fragment } from 'react';
import { connect } from 'react-redux';

import { doSearch } from '../actions';
import TopicCard from './TopicCard';
import DocumentCard from './DocumentCard';
import style from './SearchResults.module.css';

class SearchResults extends React.Component {
    state = {
        group1ClassName: 'active',
        group2ClassName: ''
    }

    componentDidMount() {
        if (!this.props.search) {
            this.props.doSearch(decodeURIComponent(this.props.match.params.query));
        }
    }

    componentDidUpdate(oldProps) {
        if (this.props.match.params.query !== oldProps.match.params.query) {
            this.props.doSearch(decodeURIComponent(this.props.match.params.query));
        }
    }

    onResultsTabClick(group) {
        const activeGroupName = `group${group}ClassName`;
        const passiveGroupName = `group${group === 1 ? 2 : 1}ClassName`;
        this.setState({...this.state, [activeGroupName]: 'active', [passiveGroupName]: ''});
    }

    renderList() {
        return this.props.search.matches.map(topic => {
            return <TopicCard key={topic.id} topic={topic} shouldRenderParents />
        });
    }

    renderSearchInfo() {
        if (!this.props.search.searchQuery) {
            return null;
        }
        let title = this.props.language === 'ar' ? `بحث في الموضوعات`
                        : `Search In Topics`;
        let resultsCount = this.props.search.matches.length;
        return (
            <a className={`item ${this.state.group1ClassName}`} onClick={() => this.onResultsTabClick(1)}>
                {title}&nbsp;
                <span className="ui red label">{resultsCount}</span>
            </a>
        );
    }

    renderOtherSearchInfo() {
        let title = this.props.language === 'ar' ? `بحث في القران`
                        : `Search In Quran`;
        let resultsCount = this.props.search.documentsMatches.length;
        return (
            <a className={`item ${this.state.group2ClassName}`} onClick={() => this.onResultsTabClick(2)}>
                {title}&nbsp;
                <span className="ui red label">{resultsCount}</span>
            </a>
        );
    }

    renderOtherList() {
        return this.props.search.documentsMatches.map(entry => {
            const entryId = `0_${entry.surah}_${entry.ayah}`;
            return <DocumentCard key={entryId} _id={entryId} entry={entry} />
        });
    }

    render() {
        if (!this.props.search) {
            return <div>Loading...</div>;
        }
        return <Fragment>
            <div className={`ui two item menu ${style.tabBar}`}>
                {this.renderSearchInfo()}
                {this.renderOtherSearchInfo()}
            </div>
            {this.state.group1ClassName === 'active' ? <Fragment>
                    {this.renderList()}
                </Fragment> : <Fragment>
                    <div>{this.renderOtherList()}</div>
                </Fragment>
            }            
        </Fragment>;
    }
}

const mapStateToProps = (state, ownProps) => {
    const query = decodeURIComponent(ownProps.match.params.query).trim();
    return {
        search: state.search[query],
        language: state.language
    };
};

export default connect(
    mapStateToProps,
    { doSearch }
)(SearchResults);
