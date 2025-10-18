from pymongo import MongoClient, ASCENDING, DESCENDING
from typing import Optional, Dict, Iterable, Tuple, List, Union
from bson.objectid import ObjectId

# Make a hashable key from dicts/lists/tuples for cache keys.
def key(obj):
    if isinstance(obj, dict):
        return tuple(sorted((k, key(v)) for k, v in obj.items()))
    if isinstance(obj, (list, tuple)):
        return tuple(key(x) for x in obj)
    return obj

# Normalize sort into PyMongo format: list of (field, ASC/DESC).
def normalSort(sort: Optional[Union[str, Iterable[Union[str, Tuple[str, int]]]]]
               ) -> Optional[List[Tuple[str, int]]]:
    if sort is None:
        return None
    if isinstance(sort, str):
        return [(sort.lstrip('-'), DESCENDING if sort.startswith('-') else ASCENDING)]
    out = []
    for s in sort:
        if isinstance(s, str):
            out.append((s.lstrip('-'), DESCENDING if s.startswith('-') else ASCENDING))
        else:
            field, direction = s
            out.append((field, ASCENDING if direction in (1, ASCENDING) else DESCENDING))
    return out


class AnimalShelter(object):
    """ CRUD operations for Animal collection in MongoDB """

    def __init__(self, username, password):
        # Initializing the MongoClient. This helps to 
        # access the MongoDB databases and collections.
        # This is hard-wired to use the aac database, the 
        # animals collection, and the aac user.
        # Definitions of the connection string variables are
        # unique to the individual Apporto environment.
        #
        # You must edit the connection variables below to reflect
        # your own instance of MongoDB!
        #
        # Connection Variables
        #
        USER = username
        PASS = password
        HOST = 'nv-desktop-services.apporto.com'
        PORT = 33977
        DB = 'AAC'
        COL = 'animals'
        #
        # Initialize Connection
        #
        self.client = MongoClient('mongodb://%s:%s@%s:%d' % (USER,PASS,HOST,PORT))
        self.database = self.client['%s' % (DB)]
        self.collection = self.database['%s' % (COL)]
        self._cache = {}

# Complete this create method to implement the C in CRUD.
    def create(self, data):
        if data is not None:
            self.database.animals.insert_one(data)  # data should be dictionary  
            self._cache.clear()
            return True
        
        else:
            raise Exception("Nothing to save, because data parameter is empty")
            return False

# Create method to implement the R in CRUD.
    def read(self,
            query: Optional[Dict] = None,
            projection: Optional[Dict] = None,
            sort: Optional[Union[str, Iterable[Union[str, Tuple[str, int]]]]] = None,
            limit: Optional[int] = None,
            skip: Optional[int] = None):
        
        # Build cache key
        norm_sort = normalSort(sort)
        key = (key(query or {}), key(projection or {}), tuple(norm_sort) if norm_sort else None, limit, skip)
        
        # Check cache
        hit = self._cache.get(key)
        if hit is not None:
            return hit

        # MongoDB query
        cursor = self.database.animals.find(query or {}, projection or None)
        if norm_sort:
            cursor = cursor.sort(norm_sort)
        if skip:
            cursor = cursor.skip(skip)
        if limit:
            cursor = cursor.limit(limit)

        results = list(cursor)
        self._cache[key] = results
        return results
    
# CRUD method to implement U
    def update(self, lookUp, newData):
        if newData is not None:
            result = self.database.animals.update_one(lookUp, {"$set": newData})
            self._cache.clear()
            return result.modified_count
        else:
            return "{}"
            
#CRUD method to implement D
    def delete(self, query):
        if query is not None:
            result = self.database.animals.delete_one(query)
            self._cache.clear()
            return result.deleted_count
        else:
            return"{}"