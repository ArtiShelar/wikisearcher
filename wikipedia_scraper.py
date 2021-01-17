import json
import wikipedia
import re

max_topics = 1250
topic_file = "Wikipedia_topics_shuffle.txt"

val_topic = 0
data_dump = []

if __name__ == '__main__':
    with open(topic_file, 'r', encoding="utf8") as tfile:
        for line in tfile:
            parts = line.replace('"','').split()
            #print(parts[0].replace('_',' '))
            title = parts[0].replace('_',' ')
            #print(title)
            try:
                result = wikipedia.page(title)
            except wikipedia.exceptions.DisambiguationError as e:
                print(title, "had disambiguation")
                title = e.options[0].replace('_', ' ')
                try:
                    result = wikipedia.page(title)
                except:
                    continue
            except wikipedia.exceptions.PageError:
                print(title, "topic not found")
                continue
            if(result):
                try:
                    if (str(result.content) and len(result.images) > 0):
                        valid_img = [s for s in result.images if "jpeg" in s]
                        if not valid_img:
                            valid_img = [s for s in result.images if "png" in s]
                        #print(valid_img[0])
                        data = {}
                        data['title'] = result.title
                        data['image'] = str(valid_img[0])

                        content = result.content
                        new_content = re.sub(r'==.*?==+', '', content).replace('\n', '')
                        #print(new_content)
                        data['content'] = new_content

                        data['summary'] = str(result.summary).replace('\n', '')
                        val_topic = val_topic+1
                        data_dump.append(data)
                        print(title, "found")
                    else:
                        print(title, "not found")
                except KeyError:
                    print(title, "had key errors")
                except:
                    print(title, "had unknown errors")
            if(val_topic > max_topics):
                with open("/src/main/resources/static/document_collection.json", "w") as text_file:
                     json.dump(data_dump, text_file)
                break
