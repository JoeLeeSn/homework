from pathlib import Path
import os
print(os.path.abspath('..'))
print(os.path.exists('/Users'))
print(os.path.isdir('/Users'))
os.path.join('/tmp/a/', 'b/c')

p = Path('.')
print(p.resolve())

p.is_dir()

q = Path('/tmp/a/b/c')

Path.mkdir(q, parents=True)
