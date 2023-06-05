import os
import urllib
import cv2
import requests
from matplotlib import pyplot as plt
from adjustText import adjust_text


class Star:
    def __init__(self, id_star, name, x, y, r):
        self.x = x
        self.y = y
        self.name = name
        self.id = str(id_star)
        self.r = r


def draw_image(img_path, st: str):
    coordinates = []
    if st == "":
        image = cv2.imread(img_path)
        filename = "%s_processed.png" % img_path
        cv2.imwrite(filename, image)
        return filename
    st_split = st.split('[')[1].split(']')[0].split(',')
    count = 0
    for s in st_split:
        if '(' in s:
            count = 1
            id_star = int(s.split('(')[1])
        elif ')' in s:
            count = 0
            r = float(s.split(')')[0])
            new_star = Star(id_star, name, x, y, r)
            coordinates.append(new_star)
        else:
            count += 1
            if count == 2:
                name = s
            elif count == 3:
                x = float(s)
            elif count == 4:
                y = float(s)

    img = cv2.imread(img_path)
    fig, ax = plt.subplots(ncols=1, figsize=(10, 10))
    fig.patch.set_facecolor('none')
    ax.imshow(img, cmap='gray')
    num = 1

    for star in coordinates:
        ax.annotate(star.id + " ", (star.x, star.y), xytext=(star.x, star.y), color='y', fontsize=20,
                    horizontalalignment='right', verticalalignment='baseline')
        ax.add_patch(plt.Circle((star.x, star.y), radius=star.r + 10, edgecolor='r', facecolor='none'))
        num += 1

    plt.tight_layout()
    plt.axis('off')

    # write an image file
    filename = "%s_processed.png" % img_path
    adjust_text(ax.texts, arrowprops=dict(arrowstyle="->", color='r', lw=0.5))
    plt.savefig(filename, transparent=True)

    return filename


def draw_by_id(img_path, st: str):
    str_split = st.split(',')
    id_star = int(str_split[0])
    name_star = str_split[1]
    x_star = float(str_split[2])
    y_star = float(str_split[3])
    r_star = float(str_split[4])
    img = cv2.imread(img_path)
    fig, ax = plt.subplots(ncols=1, figsize=(10, 10))
    fig.patch.set_facecolor('none')
    ax.imshow(img, cmap='gray')
    num = 1

    ax.annotate(str(id_star) + " ", (x_star, y_star), xytext=(x_star, y_star), color='y', fontsize=20,
                horizontalalignment='right', verticalalignment='baseline')
    ax.text(x_star, y_star, f"{name_star}", color='y', fontsize=20, horizontalalignment='left',
            verticalalignment='baseline')
    ax.add_patch(plt.Circle((x_star, y_star), radius=r_star + 10, edgecolor='r', facecolor='none'))
    num += 1

    plt.tight_layout()
    plt.axis('off')

    # write an image file
    filename = "%s_processed.png" % img_path
    adjust_text(ax.texts, arrowprops=dict(arrowstyle="->", color='r', lw=0.5))
    plt.savefig(filename, transparent=True)

    return filename


def show_all(img_path, st: str):
    coordinates = []
    if st == "":
        image = cv2.imread(img_path)
        filename = "%s_processed.png" % img_path
        cv2.imwrite(filename, image)
        return filename
    st_split = st.split('[')[1].split(']')[0].split(',')
    count = 0
    for s in st_split:
        if '(' in s:
            count = 1
            id_star = int(s.split('(')[1])
        elif ')' in s:
            count = 0
            r = float(s.split(')')[0])
            new_star = Star(id_star, name, x, y, r)
            coordinates.append(new_star)
        else:
            count += 1
            if count == 2:
                name = s
            elif count == 3:
                x = float(s)
            elif count == 4:
                y = float(s)

    img = cv2.imread(img_path)
    fig, ax = plt.subplots(ncols=1, figsize=(10, 10))
    fig.patch.set_facecolor('none')
    ax.imshow(img, cmap='gray')
    num = 1

    for star in coordinates:
        ax.annotate(star.id + " ", (star.x, star.y), xytext=(star.x, star.y), color='y', fontsize=20,
                    horizontalalignment='right', verticalalignment='baseline')
        ax.text(star.x, star.y, f"{star.name}", color='y', fontsize=20, horizontalalignment='left',
                verticalalignment='baseline')
        ax.add_patch(plt.Circle((star.x, star.y), radius=star.r + 10, edgecolor='r', facecolor='none'))
        num += 1

    plt.tight_layout()
    plt.axis('off')

    # write an image file
    filename = "%s_processed.png" % img_path
    adjust_text(ax.texts, arrowprops=dict(arrowstyle="->", color='r', lw=0.5))
    plt.savefig(filename, transparent=True)

    return filename
