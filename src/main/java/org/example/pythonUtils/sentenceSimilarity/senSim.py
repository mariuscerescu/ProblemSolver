from sentence_transformers import SentenceTransformer, util
import torch

def compute_similarity(sentence1, sentence2):
    # Load the pre-trained model
    model = SentenceTransformer('sentence-transformers/all-MiniLM-L6-v2')
    
    # Compute embeddings for both sentences
    embeddings1 = model.encode(sentence1, convert_to_tensor=True)
    embeddings2 = model.encode(sentence2, convert_to_tensor=True)
    
    # Compute cosine similarity
    cosine_similarity = util.pytorch_cos_sim(embeddings1, embeddings2)
    
    return cosine_similarity.item()

def read_first_sentence_from_file(file_path):
    with open(file_path, 'r', encoding='utf-8') as file:
        first_sentence = file.readline().strip()  # Read the first line only
    return first_sentence

def write_result_to_file(file_path, similarity):
    with open(file_path, 'w', encoding='utf-8') as file:
        file.write(f"{similarity}")

# File paths
file_path1 = "files/sentence1.txt"
file_path2 = "files/sentence2.txt"
result_file_path = "files/similarity.txt"

# Read the first sentence from each file
sentence1 = read_first_sentence_from_file(file_path1)
sentence2 = read_first_sentence_from_file(file_path2)

# Compute and print the similarity
similarity = compute_similarity(sentence1, sentence2)

# Write the result to the result file
write_result_to_file(result_file_path, similarity)