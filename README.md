# Semantic Search Engine

A Semantic Search Engine, written in Java programing language, developed with the help of Stanford coreNLP, Lucene, and WordNet softwares.

---

## Index
1. [Introduction](#introduction)
2. [Used software and Requirments](#used-software-and-requirments)
3. [Semantic Search Engine Steps](#semantic-search-engine-steps)
4. [Results](#results)

---
## Introduction

To read a small introduction, please read the [introducary report](https://github.com/Nemat-Allah-Aloush/Semantic-Search-Engine/blob/main/Introductory%20Report.pdf)

The purpose of the project is to develop a semantic search engine in the English language, the search is done within a set of news from the .bbc station, represented in an xml file, that can be found [here](https://github.com/Nemat-Allah-Aloush/Semantic-Search-Engine/blob/main/bbc_rss_feed.xml).

In order to achieve semantic research, semantic linguistic processing is carried out either on documents or on the query, and since the query is smaller, the semantic analysis is applied on it. 

--- 
## Used Software and Requirments

The project is accomplished using Java programing language, [Lucene software](https://lucene.apache.org/) for Document Indexing and Searching, [Standford CoreNlp](https://stanfordnlp.github.io/CoreNLP/) for processing both documents and query, and [WordNet](https://wordnet.princeton.edu/) for finding synonym phase during language processing process applied on the query. 

In order to run the project in a local device, you can download the project and open it in a Java IDE, for example: [Apache NetBeans](https://netbeans.apache.org/). As prerequisites, the following should softwares should be downloaded:
1. [Java JDK](https://www.oracle.com/java/technologies/downloads/).
2. [Lucene](https://lucene.apache.org/core/downloads.html).
3. [Satndford CoreNlp](https://stanfordnlp.github.io/CoreNLP/download.html).
4. [WordNet](https://wordnet.princeton.edu/download/current-version).

The jar files should be located in the [Jar folder](https://github.com/Nemat-Allah-Aloush/Semantic-Search-Engine/tree/main/Jar).

--- 
## Semantic Search Engine Steps
 In the following picture we can see the structure of the developed search engine.
![alt text](https://github.com/Nemat-Allah-Aloush/Semantic-Search-Engine/blob/main/images/SSE%20structure.png "Semantic Search Engine Steps")

We can summarize the steps in three titles:
1. [Documents processing](#document-processing).
2. [Query processing](#query-processing).
3. [Searching and showing the results](#searching-and-showing-the-results).

### Documents processing
The first step is to read the data from the [XML data file](https://github.com/Nemat-Allah-Aloush/Semantic-Search-Engine/blob/main/bbc_rss_feed.xml), and then parse it using the defined `RssFeedParser` class.

Then the parsed documents will be analyzed in the defined `DocumentAnalyzer` class. By using Standford CoreNlp functions, both the title and the content of each document are splitted to sentences. Then each sentence is splitted into tokens. Then the stop words are removed. Finally, for each token its lemma is found.

The last step in this phase is to index and save the processed documents by using the defined `LuceneWriteIndex` class, which use Lucene defined functions.

### Query processing
The first step is to read the query which was entered by the user. Then, by using the defined `QueryAnalyzer` class the same linguistic processing applied on the documents will be applied on the query. Moreover, by using CoreNlp functions, we select the Part of Speech for each token and search for synonms for it by using the defined function `SynonmsHandler`.
Finally the query to be searched for will be recreated from the lemmas of the tokens in the original query and except the stopwords and names, in addition to the lemmas of the synonms of the tokens in the original query.

### Searching and showing the results
After reading and processing the user's query, the searching of the recreated query is carried out on the defined functions in the `LuceneReadIndex` class. The searching is done among the indexed documents, and then the original documents are returned as results to be shown.

---
## Results
Here we can see some examples of user's queries and the returned results.
| Query| Titles of Returned Documents | Descriptions of Returned Documents|
| ------------- |:-------------:| :-----|
| **women**      | Indian is world's shortest **woman** | Jyoti Amge, ... world's shortest **woman** by Guinness World Records. |
|                | **Woman** set alight in New York fit  | A man is arrested after a 73-year-old **woman** is ... |
||...|
| **tells** | Riot operation 'flawed' **say** MPs|The operation to police ... a report by the Home Affairs Committee **says**.. |
|  |   Peru 'halts' parole woman's trip  |A US woman on parole in Peru.... **says** she was stopped from leaving the country.     |
||...|

